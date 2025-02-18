package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest

import de.deutschebank.recruiting.backends.uselessfacts.application.StatisticsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers.RestStatisticsMapper
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnalyticsController(
    private val statisticsService: StatisticsApplicationService,
    private val restStatisticsMapper: RestStatisticsMapper
) {

    @GET
    @Path("/statistics")
    fun getStatistics(): Response {

        val analyticsFactsSummaryDomain = statisticsService.getAccessStatistics()

        val analyticsFactsSummaryResponse = restStatisticsMapper.toStatisticsResponseFromDomain(analyticsFactsSummaryDomain)

        return Response.ok(analyticsFactsSummaryResponse).build()
    }

}