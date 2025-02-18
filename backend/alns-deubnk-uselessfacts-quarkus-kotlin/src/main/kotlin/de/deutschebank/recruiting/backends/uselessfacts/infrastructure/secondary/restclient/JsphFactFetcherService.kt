package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient

import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject


@ApplicationScoped
class JsphFactFetcherService() {

    private val logger = KotlinLogging.logger {}

    @Inject
    // @field: RestClient
    lateinit var remoteFactRestClient: RemoteFactRestClient

    fun fetchRandomFact(language: String): UselessFact? {

        var foundFact: UselessFact? = null

        val responseFromRemoteCall = remoteFactRestClient.fetchRandom(language)

        if (responseFromRemoteCall.status == 200 && responseFromRemoteCall.hasEntity()) {

            val jsphRemoteFact = responseFromRemoteCall.readEntity(JsphRemoteFact::class.java)

            logger.info { "Remote fetched fact: $jsphRemoteFact" }

            foundFact = UselessFact(
                shortenedUrl = null,
                originalId = jsphRemoteFact.id,
                text = jsphRemoteFact.text,
                language = jsphRemoteFact.language,
                permalink = jsphRemoteFact.permalink,
                statisticsMetadata = null
            )
        }

        return foundFact
    }

}