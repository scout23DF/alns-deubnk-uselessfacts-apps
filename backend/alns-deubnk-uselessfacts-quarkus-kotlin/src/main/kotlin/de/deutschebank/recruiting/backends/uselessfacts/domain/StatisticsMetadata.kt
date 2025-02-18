package de.deutschebank.recruiting.backends.uselessfacts.domain

data class StatisticsMetadata(
    val uselessFact: UselessFact,
    var hitsCount: Int = 0,
    val logAccessEntriesList: List<LogAccessEntry> = emptyList()
)
