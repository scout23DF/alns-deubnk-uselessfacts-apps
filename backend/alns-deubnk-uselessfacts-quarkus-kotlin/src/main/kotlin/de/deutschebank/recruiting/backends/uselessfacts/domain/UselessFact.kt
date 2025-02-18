package de.deutschebank.recruiting.backends.uselessfacts.domain

data class UselessFact(
    var shortenedUrl: String?,
    val originalId: String,
    val text: String,
    val language: String,
    val permalink: String,
    var statisticsMetadata: StatisticsMetadata?
)
