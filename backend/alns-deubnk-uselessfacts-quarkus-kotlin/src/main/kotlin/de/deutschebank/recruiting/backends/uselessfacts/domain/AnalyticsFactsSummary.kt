package de.deutschebank.recruiting.backends.uselessfacts.domain

data class AnalyticsFactsSummary(
    val totalFactsCount: Int = 0,
    val totalHitsCount: Int = 0,
    val mostReadUselessFact: UselessFact?,
    val leastReadUselessFact: UselessFact?,
    val othersUselessFactAttributesMap: Map<String, Int>? = emptyMap(),
    val statisticsMetadataList: List<StatisticsMetadata>? = emptyList()
)
