package de.deutschebank.recruiting.backends.uselessfacts.domain

interface FactRepository {

    fun save(newFact: UselessFact?)
    fun findById(id: String): UselessFact?
    fun findAll(): List<UselessFact>
    fun update(factToUpdate: UselessFact)
    fun delete(id: String)
    fun deleteAll()

}