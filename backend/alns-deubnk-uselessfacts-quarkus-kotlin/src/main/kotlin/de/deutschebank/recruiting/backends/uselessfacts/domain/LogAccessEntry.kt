package de.deutschebank.recruiting.backends.uselessfacts.domain

import java.time.Instant

data class LogAccessEntry(
    val statisticsMetadata: StatisticsMetadata,
    val typeOfEntry: LogAccessEntryTypeEnum,
    val accessedAt: Instant,
    val ipAddress: String,
    val hostName: String?,
    val username: String?
)

enum class LogAccessEntryTypeEnum {
    JUST_FETCHED_FROM_REMOTE_API,
    HIT_BY_A_CLIENT
}
