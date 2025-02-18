package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient

import jakarta.ws.rs.core.Response

interface RemoteFactRestClient {

    fun fetchRandom(language: String): Response

}