package de.deutschebank.recruiting.backends.uselessfacts.application

import de.deutschebank.recruiting.backends.uselessfacts.domain.*
import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import io.mockk.*
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.QuarkusTest
import io.vertx.core.http.HttpServerRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

// val factRepository: FactRepository = mockk()
val httpRequest: HttpServerRequest = mockk()

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatisticsApplicationServiceTest {

    @InjectMock
    lateinit var factRepository: FactRepository

    // Dependency Injection handled by Quarkus
    @BeforeAll
    fun setupMocks() {
        QuarkusMock.installMockForType(httpRequest, HttpServerRequest::class.java)
    }


    @Test
    @DisplayName("handleStatisticsMetadata should create new metadata and log entry for new facts")
    fun shouldCreateNewMetadataAndLogEntryForNewFacts() {

        val fakeShortenedUrl = "SomeShotenedURL"
        val fakeStoredFact = UselessFact(
            shortenedUrl = fakeShortenedUrl,
            originalId = "SomeOriginalID",
            text = "SomeLongTextHere - SomeLongTextHere",
            language = "en",
            permalink = "http://example.com/fact_perma",
            statisticsMetadata = null
        )

        every { httpRequest.getHeader("X-Forwarded-For") } returns "127.0.0.1"
        every { httpRequest.remoteAddress().host() } returns "127.0.0.1"
        every { httpRequest.getHeader("username") } returns "testuser"
        every { factRepository.update(any()) } just runs

        var statisticsApplicationService = StatisticsApplicationService(factRepository)

        statisticsApplicationService.handleStatisticsMetadata(true, fakeStoredFact, httpRequest)

        assertNotNull(fakeStoredFact.statisticsMetadata)
        assertEquals(0, fakeStoredFact.statisticsMetadata!!.hitsCount)
        assertEquals(1, fakeStoredFact.statisticsMetadata!!.logAccessEntriesList.size)

        val logEntry = fakeStoredFact.statisticsMetadata!!.logAccessEntriesList.first()
        assertEquals(LogAccessEntryTypeEnum.JUST_FETCHED_FROM_REMOTE_API, logEntry.typeOfEntry)
        assertEquals("127.0.0.1", logEntry.ipAddress)
        assertEquals("testuser", logEntry.username)

    }

    @Test
    @DisplayName("handleStatisticsMetadata should update hitsCount and log entry for existing facts")
    fun shouldUpdateHitsCountAndLogEntryForExistingFacts() {

        val fakeShortenedUrl = "SomeShotenedURL"
        val fakeStoredFact = UselessFact(
            shortenedUrl = fakeShortenedUrl,
            originalId = "SomeOriginalID",
            text = "SomeLongTextHere - SomeLongTextHere",
            language = "en",
            permalink = "http://example.com/fact_perma",
            statisticsMetadata = null
        )

        val statisticsMetadata = StatisticsMetadata(
            uselessFact = null,
            hitsCount = 5,
            logAccessEntriesList = emptyList()
        )

        fakeStoredFact.statisticsMetadata = statisticsMetadata

        every { httpRequest.getHeader("X-Forwarded-For") } returns "192.168.1.1"
        every { httpRequest.remoteAddress().host() } returns "192.168.1.1"
        every { httpRequest.getHeader("username") } returns null
        every { factRepository.update(any()) } just runs

        var statisticsApplicationService = StatisticsApplicationService(factRepository)

        statisticsApplicationService.handleStatisticsMetadata(false, fakeStoredFact, httpRequest)

        assertNotNull(fakeStoredFact.statisticsMetadata)
        assertEquals(6, fakeStoredFact.statisticsMetadata!!.hitsCount)
        assertEquals(1, fakeStoredFact.statisticsMetadata!!.logAccessEntriesList.size)

        val logEntry = fakeStoredFact.statisticsMetadata!!.logAccessEntriesList.first()
        assertEquals(LogAccessEntryTypeEnum.HIT_BY_A_CLIENT, logEntry.typeOfEntry)
        assertEquals("192.168.1.1", logEntry.ipAddress)
        assertNull(logEntry.username)

        verify { factRepository.update(fakeStoredFact) }
    }

    @Test
    @DisplayName("getAccessStatistics should return summary with correct data")
    fun shouldReturnSummaryWithCorrectData() {

        val fakeStoredFact1 = UselessFact(
            shortenedUrl = "fakeShortenedUrl-1",
            originalId = "SomeOriginalID-1",
            text = "SomeLongTextHere - SomeLongTextHere-1",
            language = "en",
            permalink = "http://example.com/fact_perma-1",
            statisticsMetadata = null
        )
        val statisticsMetadata1 = StatisticsMetadata(
            uselessFact = null,
            hitsCount = 10,
            logAccessEntriesList = emptyList()
        )
        fakeStoredFact1.statisticsMetadata = statisticsMetadata1

        val fakeStoredFact2 = UselessFact(
            shortenedUrl = "fakeShortenedUrl-2",
            originalId = "SomeOriginalID-2",
            text = "SomeLongTextHere - SomeLongTextHere-21",
            language = "en",
            permalink = "http://example.com/fact_perma-2",
            statisticsMetadata = null
        )
        val statisticsMetadata2 = StatisticsMetadata(
            uselessFact = null,
            hitsCount = 5,
            logAccessEntriesList = emptyList()
        )
        fakeStoredFact2.statisticsMetadata = statisticsMetadata2

        val fakeStoredFact3 = UselessFact(
            shortenedUrl = "fakeShortenedUrl-3",
            originalId = "SomeOriginalID-3",
            text = "SomeLongTextHere - SomeLongTextHere-3",
            language = "en",
            permalink = "http://example.com/fact_perma-3",
            statisticsMetadata = null
        )
        val statisticsMetadata3 = StatisticsMetadata(
            uselessFact = null,
            hitsCount = 0,
            logAccessEntriesList = emptyList()
        )
        fakeStoredFact3.statisticsMetadata = statisticsMetadata3

        every { factRepository.findAll() } returns listOf(fakeStoredFact1, fakeStoredFact2, fakeStoredFact3)

        var statisticsApplicationService = StatisticsApplicationService(factRepository)

        val result = statisticsApplicationService.getAccessStatistics()

        assertEquals(3, result.totalFactsCount)
        assertEquals(15, result.totalHitsCount)
        assertEquals(fakeStoredFact1, result.mostReadUselessFact)
        assertEquals(fakeStoredFact3, result.leastReadUselessFact)
        assertTrue(result.othersUselessFactAttributesMap?.isEmpty() == true)
        assertEquals(3, result.statisticsMetadataList?.size)

        verify { factRepository.findAll() }
    }

}