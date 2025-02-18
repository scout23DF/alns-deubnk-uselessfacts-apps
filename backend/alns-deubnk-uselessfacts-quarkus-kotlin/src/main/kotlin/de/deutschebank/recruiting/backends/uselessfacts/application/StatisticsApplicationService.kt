package de.deutschebank.recruiting.backends.uselessfacts.application

import de.deutschebank.recruiting.backends.uselessfacts.domain.*
import io.vertx.core.http.HttpServerRequest
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant

@ApplicationScoped
class StatisticsApplicationService(
    private val factRepository: FactRepository
) {

    fun handleStatisticsMetadata(isNew: Boolean, targetFact: UselessFact, httpRequest: HttpServerRequest) {

        val clientIp = httpRequest.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()?.trim()
            ?: httpRequest.remoteAddress().host()
        val clientHostName = java.net.InetAddress.getByName(clientIp).hostName
        val clientUsername = httpRequest.getHeader("username")
        val typeOfEntryEnum = when {
            isNew -> LogAccessEntryTypeEnum.JUST_FETCHED_FROM_REMOTE_API
            else -> LogAccessEntryTypeEnum.HIT_BY_A_CLIENT
        }

        val statisticsMetadata = targetFact.statisticsMetadata ?: StatisticsMetadata(targetFact).apply {
            hitsCount = 0
        }

        if (!isNew) {
            statisticsMetadata.hitsCount++
        }

        val newLogEntry = LogAccessEntry(
            statisticsMetadata = statisticsMetadata,
            typeOfEntry = typeOfEntryEnum,
            accessedAt = Instant.now(),
            ipAddress = clientIp,
            hostName = clientHostName,
            username = clientUsername
        )

        targetFact.statisticsMetadata = statisticsMetadata.copy(
            logAccessEntriesList = statisticsMetadata.logAccessEntriesList + newLogEntry
        )

        this.factRepository.update(targetFact)
    }

    fun getAccessStatistics(): AnalyticsFactsSummary {
        val allFacts = this.factRepository.findAll()
        val statisticsMetadataList = allFacts.mapNotNull { it.statisticsMetadata }
        val extraAnalyticsDataMap = calculateExtraAnalyticsData(statisticsMetadataList)

        return AnalyticsFactsSummary(
            totalFactsCount = allFacts.size,
            totalHitsCount = allFacts.sumOf { it.statisticsMetadata?.hitsCount ?: 0 },
            mostReadUselessFact = allFacts.filter { it.statisticsMetadata?.let { it1 -> it1.hitsCount > 0 } == true }.maxByOrNull { it.statisticsMetadata?.hitsCount ?: 0 },
            leastReadUselessFact = allFacts.minByOrNull { it.statisticsMetadata?.hitsCount ?: 0 },
            othersUselessFactAttributesMap = extraAnalyticsDataMap,
            statisticsMetadataList = statisticsMetadataList
        )
    }

    private fun calculateExtraAnalyticsData(metadata: List<StatisticsMetadata>) : Map<String, Int> {
        return emptyMap();
    }

}
