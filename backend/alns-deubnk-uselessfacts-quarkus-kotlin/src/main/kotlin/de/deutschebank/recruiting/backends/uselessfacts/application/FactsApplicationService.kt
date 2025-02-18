package de.deutschebank.recruiting.backends.uselessfacts.application

import de.deutschebank.recruiting.backends.shared.strings.domain.DBStringUtils
import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import de.deutschebank.recruiting.backends.uselessfacts.domain.FactRepository
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient.JsphFactFetcherService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FactsApplicationService(
    private val factRepository: FactRepository,
    private val jsphFactFetcherService: JsphFactFetcherService,
    private val dbStringUtils: DBStringUtils
) {

    private val logger = KotlinLogging.logger {}

    fun fetchAndSaveRandomFact(language: String): UselessFact? {

        var remoteFetchedFact : UselessFact?
        var newShortenedUrl : String?
        var alreadyExists : Boolean = false

        do {

            remoteFetchedFact = jsphFactFetcherService.fetchRandomFact(language)

            newShortenedUrl = remoteFetchedFact?.permalink?.let {
                dbStringUtils.generateShortUrl(it)
            }

            alreadyExists = newShortenedUrl?.let { this.factRepository.findById(it) } != null

            logger.info { "New shortened URL: $newShortenedUrl | Already exists: [$alreadyExists]" }

        } while (alreadyExists)

        remoteFetchedFact?.shortenedUrl = newShortenedUrl

        factRepository.save(remoteFetchedFact)

        return remoteFetchedFact
    }

    fun getFactByShortenedUrl(shortenedUrl: String): UselessFact? {
        return factRepository.findById(shortenedUrl)
    }

    fun getAllFacts(): List<UselessFact> {
        return factRepository.findAll()
    }

}
