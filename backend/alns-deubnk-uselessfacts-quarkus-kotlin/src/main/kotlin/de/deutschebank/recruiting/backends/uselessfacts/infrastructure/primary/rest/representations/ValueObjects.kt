package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations

import java.time.Instant

open class UselessFactResponse(
    var shortenedUrl: String,
    val text: String,
    val language: String,
    val permalink: String
)

class UselessFactWithStatsResponse(
    shortenedUrl: String,
    text: String,
    language: String,
    permalink: String,
    val hitsCount: Int? = 0
) : UselessFactResponse(shortenedUrl, text, language, permalink)

data class StatisticsMetadataResponse(
    var uselessFactResponse: UselessFactResponse,
    var hitsCount: Int = 0,
    val logAccessEntriesList: List<LogAccessEntryResponse> = emptyList()
)

data class LogAccessEntryResponse(
    val typeOfEntry: String,
    val accessedAt: Instant,
    val ipAddress: String,
    val hostName: String,
    val username: String
)

data class AnalyticsFactsSummaryResponse(
    val totalFactsCount: Int = 0,
    val totalHitsCount: Int = 0,
    val mostReadUselessFact: UselessFactWithStatsResponse?,
    val leastReadUselessFact: UselessFactWithStatsResponse?,
    val othersUselessFactAttributesMap: Map<String, Int>? = emptyMap(),
    val statisticsMetadataList: List<StatisticsMetadataResponse>? = emptyList()
)