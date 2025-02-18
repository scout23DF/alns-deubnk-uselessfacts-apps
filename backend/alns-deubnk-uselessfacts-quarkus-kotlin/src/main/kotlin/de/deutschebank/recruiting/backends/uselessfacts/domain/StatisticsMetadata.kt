package de.deutschebank.recruiting.backends.uselessfacts.domain

data class StatisticsMetadata(
    var uselessFact: UselessFact?,
    var hitsCount: Int = 0,
    var logAccessEntriesList: List<LogAccessEntry> = emptyList()
)
