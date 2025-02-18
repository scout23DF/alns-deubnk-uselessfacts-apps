package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.persistence

import de.deutschebank.recruiting.backends.uselessfacts.domain.UselessFact
import de.deutschebank.recruiting.backends.uselessfacts.domain.FactRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.enterprise.context.ApplicationScoped
import java.util.concurrent.ConcurrentHashMap

@ApplicationScoped
class InMemoryFactRepository: FactRepository {

    private val logger = KotlinLogging.logger {}

    private val factsCacheMap = ConcurrentHashMap<String, UselessFact>()

    override fun save(newFact: UselessFact?) {
        if (factsCacheMap.containsKey(newFact?.shortenedUrl)) {
            throw IllegalStateException("Fact already exists for $newFact.shortenedUrl")
        }
        newFact?.let { factsCacheMap.put(newFact.shortenedUrl.toString(), it) }
    }

    override fun findById(id: String): UselessFact? {
        return factsCacheMap[id]
    }

    override fun findAll(): List<UselessFact> {
        return factsCacheMap.values.toList()
    }

    override fun update(factToUpdate: UselessFact) {
        if (!factsCacheMap.containsKey(factToUpdate.shortenedUrl)) {
            throw IllegalStateException("Fact not exists for $factToUpdate.shortenedUrl")
        }
        factsCacheMap[factToUpdate.shortenedUrl.toString()] = factToUpdate
    }

    override fun delete(id: String) {
        if (factsCacheMap.containsKey(id)) {
            factsCacheMap.remove(id)
        }
    }

}