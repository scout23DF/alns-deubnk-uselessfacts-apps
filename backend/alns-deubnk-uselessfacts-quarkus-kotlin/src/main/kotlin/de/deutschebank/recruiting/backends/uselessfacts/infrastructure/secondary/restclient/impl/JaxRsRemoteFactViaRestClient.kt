package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.impl

import com.fasterxml.jackson.annotation.JsonProperty
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.JsphFactFetcherService
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.JsphRemoteFact
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.RemoteFactRestClient
import io.smallrye.faulttolerance.api.RateLimit
import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Alternative
import jakarta.inject.Inject
import jakarta.ws.rs.client.Client
import jakarta.ws.rs.client.ClientBuilder
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.faulttolerance.Fallback
import org.eclipse.microprofile.faulttolerance.Retry
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.String
import kotlin.uuid.Uuid


@Alternative
@Priority(1)
@ApplicationScoped
class JaxRsRemoteFactViaRestClient(
    @ConfigProperty(name = "useless-facts.rest-client.jax-rs.remote.base-url")
    private val baseUrl: String
): RemoteFactRestClient {

    private val executorService: ExecutorService = Executors.newCachedThreadPool()

    @Retry(maxRetries = 4)
    @Fallback(fallbackMethod = "fallbackFakeFetchRandom")
    @CircuitBreaker(requestVolumeThreshold = 4)
    @RateLimit(value = 200, window = 60, windowUnit = ChronoUnit.SECONDS)
    override fun fetchRandom(language: String): Response {

        var responseFactFetched: Response
        var jaxRsClient : Client = ClientBuilder.newBuilder()
                .connectTimeout(1, TimeUnit.SECONDS) // Connection Timeout
                .readTimeout(2, TimeUnit.SECONDS) // Read Timeout
                .executorService(executorService)
                .build()

            responseFactFetched = jaxRsClient
                .target(this.baseUrl + "/facts/random")
                .queryParam("language", language)
                .request()
                .get()

        jaxRsClient.close()

        return responseFactFetched
    }

    fun fallbackFakeFetchRandom(language: String): Response {
        var tempId = "fallbackId-" + UUID.randomUUID()

        val fakeJsphRemoteFact = JsphRemoteFact(
            id = tempId,
            text = "It is only a fallback Text for the JaxRsRemoteFactViaRestClient :: " + LocalDateTime.now(),
            source = "Fallback Source",
            sourceUrl = "http://localhost:8080",
            language = "en",
            permalink = "http://localhost:8080/api/facts/$tempId"
        )
        return Response.ok(fakeJsphRemoteFact).build()
    }

}
