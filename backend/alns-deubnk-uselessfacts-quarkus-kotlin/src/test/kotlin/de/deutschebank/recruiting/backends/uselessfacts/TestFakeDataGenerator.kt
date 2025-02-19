package de.deutschebank.recruiting.backends.uselessfacts

import de.deutschebank.recruiting.backends.uselessfacts.domain.FactRepository
import de.deutschebank.recruiting.backends.uselessfacts.domain.StatisticsMetadata
import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.StatisticsMetadataResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.UselessFactResponse

class TestFakeDataGenerator {

    companion object {

        fun createFakeDomainFact(
            baseContent: String,
            fakeHitCount: Int,
            shouldPersist: Boolean = true,
            factsRepository: FactRepository? = null
        ): UselessFact {
            val fakeStoredFact = UselessFact(
                shortenedUrl = "fakeShortenedUrl-$baseContent",
                originalId = "SomeOriginalID-$baseContent",
                text = "SomeLongTextHere :: $baseContent",
                language = "en",
                permalink = "http://example.com/fact_perma-$baseContent",
                statisticsMetadata = null
            )
            val statisticsMetadata = StatisticsMetadata(
                uselessFact = null,
                hitsCount = fakeHitCount,
                logAccessEntriesList = emptyList()
            )
            fakeStoredFact.statisticsMetadata = statisticsMetadata

            if (shouldPersist) {
                factsRepository?.save(fakeStoredFact)
            }

            return fakeStoredFact
        }

        fun createFakeFactResponse(baseContent: String, fakeHitCount: Int): UselessFactResponse {
            val fakeUselessFactResponse = UselessFactResponse(
                shortenedUrl = "fakeShortenedUrl-$baseContent",
                text = "SomeLongTextHere :: $baseContent",
                language = "en",
                permalink = "http://example.com/fact_perma-$baseContent",
            )
            val statisticsMetadata = StatisticsMetadataResponse(
                uselessFactResponse = fakeUselessFactResponse,
                hitsCount = fakeHitCount,
                logAccessEntriesList = emptyList()
            )

            return fakeUselessFactResponse
        }

    }

}