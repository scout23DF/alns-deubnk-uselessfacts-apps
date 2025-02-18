package de.deutschebank.recruiting.backends.uselessfacts.infrastructure.secondary.restclient

import com.fasterxml.jackson.annotation.JsonProperty

data class JsphRemoteFact(
    val id: String,
    val text: String,
    val source: String,
    @JsonProperty("source_url")
    val sourceUrl: String,
    val language: String,
    val permalink: String
)
/* Sample of Response from API: https://uselessfacts.jsph.pl/api/v2/facts/random?language=en
{
    "id": "11019ab41d0669f3848468060c5322b2",
    "text": "Dolphins sleep with one eye open!",
    "source": "djtech.net",
    "source_url": "http://www.djtech.net/humor/useless_facts.htm",
    "language": "en",
    "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/11019ab41d0669f3848468060c5322b2"
}
*/