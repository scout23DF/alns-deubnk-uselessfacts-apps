package de.deutschebank.recruiting.backends.shared.filters

import jakarta.annotation.Priority
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.container.ResourceInfo
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.eclipse.microprofile.config.inject.ConfigProperty

@Provider
@Priority(1000) // High priority
class ApiKeyVerifierFilter(
    @ConfigProperty(name = "useless-facts.rest.api.security.api-key.value")
    private val configSecretAPIKey: String
) : ContainerRequestFilter {

    @Context
    lateinit var resourceInfo: ResourceInfo

    override fun filter(requestContext: ContainerRequestContext) {
        val path = requestContext.uriInfo.path

        if (path.contains("/api/admin/")) {
            val apiKey = requestContext.getHeaderString("X-API-KEY")

            if (apiKey == null || apiKey != configSecretAPIKey) {

                requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Unauthorized Access [From ApiKeyVerifierFilter].")
                        .build()

                )
            }
        }
    }
}