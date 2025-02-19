package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest

import de.deutschebank.recruiting.backends.uselessfacts.application.FactsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.application.StatisticsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.domain.FactRepository
import de.deutschebank.recruiting.backends.uselessfacts.domain.StatisticsMetadata
import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers.RestFactsMapper
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.StatisticsMetadataResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.UselessFactResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.JsphFactFetcherService
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.response.Response
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertContains
import kotlin.test.assertNotNull

@QuarkusTest
class FactsControllerIT {

    @InjectMocks
    private lateinit var factsController: FactsController

    @Inject
    private lateinit var factsRepository: FactRepository

    @Mock
    private lateinit var factService: FactsApplicationService

    @Mock
    private lateinit var statisticsService: StatisticsApplicationService

    @Mock
    private lateinit var restFactsMapper: RestFactsMapper

    @Mock
    private lateinit var jsphFactFetcherService: JsphFactFetcherService

    init {
        MockitoAnnotations.openMocks(this)
    }

    @BeforeEach
    fun setupAndPrepare() {
        factsRepository.deleteAll()
    }

    @Test
    @DisplayName("fetchFact should fetch and save one UselessFact and return HTTP 201")
    fun shouldFetchAndSaveOneUselessFactWithHTTP201() {

        var baseContent = "Fact#1"
        var fakeHitCount = 5

        val fakeStoredFact = createFakeDomainFact("Fact#1", fakeHitCount, false)

        `when`(jsphFactFetcherService.fetchRandomFact("en")).thenReturn(fakeStoredFact)

        // Act
        val response: Response = RestAssured.given()
            .`when`().post("/api/facts")
            .then().statusCode(201)
            .extract()
            .response()

        // Assert
        val returnedFact = response.jsonPath().getObject(".", UselessFactResponse::class.java)
        assertNotNull(returnedFact)
    }

    @Test
    @DisplayName("getAllFacts should return empty list with HTTP 200")
    fun shouldReturnAllFactsWithHTTP200() {

        val domainFacts = listOf(
            createFakeDomainFact("Fact#1", 5),
            createFakeDomainFact("Fact#2", 8),
            createFakeDomainFact("Fact#3", 7),
            createFakeDomainFact("Fact#4", 4)
        )
        val responseFacts = listOf(
            createFakeFactResponse("Fact#1", 5),
            createFakeFactResponse("Fact#2", 8),
            createFakeFactResponse("Fact#3", 7),
            createFakeFactResponse("Fact#4", 4)
        )

        `when`(factService.getAllFacts()).thenReturn(domainFacts)
        `when`(restFactsMapper.toFactResponseFromDomain(domainFacts[0])).thenReturn(responseFacts[0])
        `when`(restFactsMapper.toFactResponseFromDomain(domainFacts[1])).thenReturn(responseFacts[1])
        `when`(restFactsMapper.toFactResponseFromDomain(domainFacts[2])).thenReturn(responseFacts[2])
        `when`(restFactsMapper.toFactResponseFromDomain(domainFacts[3])).thenReturn(responseFacts[3])

        // Act
        val response: Response = RestAssured.given()
            .`when`().get("/api/facts")
            .then().statusCode(200)
            .extract()
            .response()

        // Assert
        val returnedFacts = response.jsonPath().getList(".", UselessFactResponse::class.java)
        assertEquals(4, returnedFacts.size)
        assertContains(returnedFacts[0].text, "Fact#1")
        assertContains(returnedFacts[1].text, "Fact#2")
        assertContains(returnedFacts[2].text, "Fact#3")
        assertContains(returnedFacts[3].text, "Fact#4")
    }

    private fun createFakeDomainFact(baseContent: String, fakeHitCount: Int, shouldPersist: Boolean = true): UselessFact {
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
            factsRepository.save(fakeStoredFact)
        }

        return fakeStoredFact
    }

    private fun createFakeFactResponse(baseContent: String, fakeHitCount: Int): UselessFactResponse {
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