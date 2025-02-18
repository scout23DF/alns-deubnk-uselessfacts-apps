package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.mappers

import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import de.deutschebank.recruiting.backends.uselessfacts.infrastructure.primary.rest.representations.UselessFactResponse
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RestFactsMapper() {

    fun toFactResponseFromDomain(fact: UselessFact): UselessFactResponse {
        return UselessFactResponse(
            shortenedUrl = fact.shortenedUrl ?: "Not available",
            text = fact.text,
            language = fact.language,
            permalink = fact.permalink
        )
    }

}
