package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers

import de.deutschebank.recruiting.backends.uselessfacts.domain.AnalyticsFactsSummary
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.AnalyticsFactsSummaryResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.LogAccessEntryResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.StatisticsMetadataResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.UselessFactResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.UselessFactWithStatsResponse
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RestStatisticsMapper() {

    fun toStatisticsResponseFromDomain(analyticsFactsSummary: AnalyticsFactsSummary): AnalyticsFactsSummaryResponse {

        return AnalyticsFactsSummaryResponse(
            totalFactsCount = analyticsFactsSummary.totalFactsCount,
            totalHitsCount = analyticsFactsSummary.totalHitsCount,
            mostReadUselessFact = UselessFactWithStatsResponse(
                shortenedUrl = analyticsFactsSummary.mostReadUselessFact?.shortenedUrl.toString(),
                text = analyticsFactsSummary.mostReadUselessFact?.text.toString(),
                language = analyticsFactsSummary.mostReadUselessFact?.language.toString(),
                permalink = analyticsFactsSummary.mostReadUselessFact?.permalink.toString(),
                hitsCount = analyticsFactsSummary.mostReadUselessFact?.statisticsMetadata?.hitsCount
            ),
            leastReadUselessFact = UselessFactWithStatsResponse(
                shortenedUrl = analyticsFactsSummary.leastReadUselessFact?.shortenedUrl.toString(),
                text = analyticsFactsSummary.leastReadUselessFact?.text.toString(),
                language = analyticsFactsSummary.leastReadUselessFact?.language.toString(),
                permalink = analyticsFactsSummary.leastReadUselessFact?.permalink.toString(),
                hitsCount = analyticsFactsSummary.leastReadUselessFact?.statisticsMetadata?.hitsCount
            ),
            othersUselessFactAttributesMap = analyticsFactsSummary.othersUselessFactAttributesMap,
            statisticsMetadataList = analyticsFactsSummary.statisticsMetadataList?.map { metadata ->
                StatisticsMetadataResponse(
                    uselessFactResponse = UselessFactResponse(
                        shortenedUrl = metadata.uselessFact?.shortenedUrl.toString(),
                        text = metadata.uselessFact?.text.toString(),
                        language = metadata.uselessFact?.language.toString(),
                        permalink = metadata.uselessFact?.permalink.toString(),
                    ),
                    hitsCount = metadata.hitsCount,
                    logAccessEntriesList = metadata.logAccessEntriesList.map { logEntry ->
                        LogAccessEntryResponse(
                            typeOfEntry = logEntry.typeOfEntry.toString(),
                            accessedAt = logEntry.accessedAt,
                            ipAddress = logEntry.ipAddress,
                            hostName = logEntry.hostName.toString(),
                            username = logEntry.username.toString()
                        )
                    }
                )
            }
        )

    }

}
