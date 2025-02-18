package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest

import de.deutschebank.recruiting.backends.uselessfacts.application.FactsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.application.StatisticsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers.RestFactsMapper
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers.RestStatisticsMapper
import jakarta.ws.rs.Path


@Path("/api")
class ApiRootController(
    private val factService: FactsApplicationService,
    private val statisticsService: StatisticsApplicationService,
    private val restFactsMapper: RestFactsMapper,
    private val restStatisticsMapper: RestStatisticsMapper
) {

    @Path("/facts")
    fun handleFactsApi(): FactsController {
        return FactsController(factService, statisticsService, restFactsMapper)
    }

    @Path("/admin")
    fun handleAdminApi(): AnalyticsController {
        return AnalyticsController(statisticsService, restStatisticsMapper)
    }

}