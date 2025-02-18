package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.impl

import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.RemoteFactRestClient
import io.quarkus.arc.DefaultBean
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@DefaultBean
@Path("/facts")
@RegisterRestClient(configKey = "remote-uselessfacts-api")
interface QrkExtensionRemoteFactViaRestClient : RemoteFactRestClient {

    @GET
    @Path("/random")
    override fun fetchRandom(@QueryParam("language") language: String): Response

}