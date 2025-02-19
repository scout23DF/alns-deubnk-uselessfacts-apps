package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest

import de.deutschebank.recruiting.backends.uselessfacts.TestFakeDataGenerator
import de.deutschebank.recruiting.backends.uselessfacts.application.FactsApplicationService
import de.deutschebank.recruiting.backends.uselessfacts.domain.FactRepository
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers.RestFactsMapper
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
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertContains
import kotlin.test.assertNotNull

@QuarkusTest
class FactsControllerIT {

    @Inject
    private lateinit var factsRepository: FactRepository

    @Mock
    private lateinit var factService: FactsApplicationService

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

        val baseContent = "Fact#1"
        val fakeHitCount = 5

        val fakeStoredFact = TestFakeDataGenerator.createFakeDomainFact(baseContent, fakeHitCount, false)

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
            TestFakeDataGenerator.createFakeDomainFact("Fact#1", 5, true, factsRepository),
            TestFakeDataGenerator.createFakeDomainFact("Fact#2", 8,true, factsRepository),
            TestFakeDataGenerator.createFakeDomainFact("Fact#3", 7, true, factsRepository),
            TestFakeDataGenerator.createFakeDomainFact("Fact#4", 4, true, factsRepository),
        )
        val responseFacts = listOf(
            TestFakeDataGenerator.createFakeFactResponse("Fact#1", 5),
            TestFakeDataGenerator.createFakeFactResponse("Fact#2", 8),
            TestFakeDataGenerator.createFakeFactResponse("Fact#3", 7),
            TestFakeDataGenerator.createFakeFactResponse("Fact#4", 4)
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

}