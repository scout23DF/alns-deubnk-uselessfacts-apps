package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest

import de.deutschebank.recruiting.backends.uselessfacts.application.FactsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.application.StatisticsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers.RestFactsMapper
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.UselessFactResponse
import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FactsController(
    private val factService: FactsApplicationService,
    private val statisticsService: StatisticsApplicationService,
    private val restFactsMapper: RestFactsMapper
) {

    @POST
    fun fetchFact(@Context httpRequest: HttpServerRequest): Response {
        var resultResponse : Response
        val newFact = factService.fetchAndSaveRandomFact("en")

        if (newFact != null) {
            statisticsService.handleStatisticsMetadata(true, newFact, httpRequest)
            val newFactResponse : UselessFactResponse = restFactsMapper.toFactResponseFromDomain(newFact)
            resultResponse = Response.status(Response.Status.CREATED).entity(newFactResponse).build()
        } else {
            resultResponse = Response.status(Response.Status.BAD_REQUEST).build()
        }

        return resultResponse
    }

    @GET
    fun getAllFacts(): Response {
        val foundFactsList = factService.getAllFacts()

        val foundFactsResponseList = foundFactsList.map { restFactsMapper.toFactResponseFromDomain(it) }

        return Response.ok(foundFactsResponseList).build()
    }

    @GET
    @Path("/{shortenedUrl}")
    fun getFact(@PathParam("shortenedUrl") shortenedUrl: String,
                @Context httpRequest: HttpServerRequest): Response {

        var resultResponse : Response
        val foundFact = factService.getFactByShortenedUrl(shortenedUrl)

        if (foundFact != null) {
            statisticsService.handleStatisticsMetadata(false, foundFact, httpRequest)
            val foundFactResponse : UselessFactResponse = restFactsMapper.toFactResponseFromDomain(foundFact)
            resultResponse = Response.ok(foundFactResponse).build()
        } else {
            resultResponse = Response.status(Response.Status.NOT_FOUND).build()
        }

        return resultResponse
    }

}