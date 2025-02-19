package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest

import de.deutschebank.recruiting.backends.uselessfacts.domain.FactRepository
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.AnalyticsFactsSummaryResponse
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.UselessFactResponse
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertNotNull

@QuarkusTest
class AnalyticsControllerTest {

    @Inject
    private lateinit var factsRepository: FactRepository

    @ConfigProperty(name = "useless-facts.rest.api.security.api-key.value")
    lateinit var configSecretAPIKey: String

    init {
        MockitoAnnotations.openMocks(this)
    }

    @BeforeEach
    fun setupAndPrepare() {
        factsRepository.deleteAll()
    }

    @Test
    fun testGetStatistics() {

        val qtyFacts = 8
        val qtyReads = 20

        simulateNFetchFactsWithXReadsEach(qtyFacts, qtyReads)

        // Perform the GET request
        val analyticsFactsSummary : AnalyticsFactsSummaryResponse = RestAssured.given()
            .header("X-API-KEY", configSecretAPIKey)
            .`when`().get("/api/admin/statistics")
            .then().statusCode(200)
            .extract()
            .response()
            .jsonPath().getObject(".", AnalyticsFactsSummaryResponse::class.java)

        // Validate the response
        assertNotNull(analyticsFactsSummary)
        assertEquals(qtyFacts, analyticsFactsSummary.totalFactsCount)
        assertEquals(qtyFacts * qtyReads, analyticsFactsSummary.totalHitsCount)
        assertEquals(qtyFacts, analyticsFactsSummary.statisticsMetadataList?.size)
        assertEquals(qtyReads, analyticsFactsSummary.statisticsMetadataList?.get(0)?.hitsCount)
        assertEquals(qtyReads,
            analyticsFactsSummary.statisticsMetadataList?.get(0)?.logAccessEntriesList?.size?.minus(1) ?: 0
        )
    }

    private fun simulateNFetchFactsWithXReadsEach(qtyFacts: Int, qtyReads: Int) {

        for (i in 1..qtyFacts) {
            // val fact = TestFakeDataGenerator.createFakeDomainFact("Fact#$i", 0, true, factsRepository)

            // Perform the POST request
            val returnedFact: UselessFactResponse = RestAssured.given()
                .`when`().post("/api/facts")
                .then().statusCode(201)
                .extract()
                .response()
                .jsonPath().getObject(".", UselessFactResponse::class.java)

            for (j in 1..qtyReads) {
                // Perform the GET request
                RestAssured.given()
                    .`when`().get("/api/facts/${returnedFact.shortenedUrl}")
                    .then().statusCode(200)

            }

        }

    }

}