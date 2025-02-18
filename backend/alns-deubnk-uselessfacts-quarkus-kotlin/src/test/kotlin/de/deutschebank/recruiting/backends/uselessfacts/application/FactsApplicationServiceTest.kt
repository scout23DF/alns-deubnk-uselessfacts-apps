package de.deutschebank.recruiting.backends.uselessfacts.application

import de.deutschebank.recruiting.backends.shared.strings.domain.DBStringUtils
import de.deutschebank.recruiting.backends.uselessfacts.domain.FactRepository
import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.JsphFactFetcherService
import io.mockk.*
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertNull

val jsphFactFetcherService: JsphFactFetcherService = mockk()

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FactsApplicationServiceTest {

    @Inject
    lateinit var factsApplicationService: FactsApplicationService
    @Inject
    lateinit var factRepository: FactRepository
    @InjectMock
    lateinit var dbStringUtils: DBStringUtils

    // Dependency Injection handled by Quarkus
    @BeforeAll
    fun setupMocks() {
        QuarkusMock.installMockForType(jsphFactFetcherService, JsphFactFetcherService::class.java)
    }


    @Test
    fun `should return null if fact cannot be fetched`() {
        every { jsphFactFetcherService.fetchRandomFact("en") } returns null

        val result = factsApplicationService.fetchAndSaveRandomFact("en")

        assertNull(result)
    }

    @Test
    fun `should fetch and save a random fact`() {
        // Mock a remote fetched fact
        val fakeShortenedUrl = "SomeShotenedURL"

        val fakeStoredFact = UselessFact(
            shortenedUrl = fakeShortenedUrl,
            originalId = "SomeOriginalID",
            text = "SomeLongTextHere - SomeLongTextHere",
            language = "en",
            permalink = "http://example.com/fact_perma",
            statisticsMetadata = null
        )

        // Mock CDI-Injected behavior
        every { jsphFactFetcherService.fetchRandomFact("en") } returns fakeStoredFact

        every { dbStringUtils.generateShortUrl(fakeStoredFact.permalink!!) } returns fakeShortenedUrl

        val result = factsApplicationService.fetchAndSaveRandomFact("en")

        assertEquals(fakeStoredFact, result)
        assertEquals(fakeShortenedUrl, result?.shortenedUrl)

    }

}