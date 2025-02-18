package de.deutschebank.recruiting.backends.shared.rest.errors

data class ErrorResponseHolder(
    val friendlyErrorMessage : String,
    val exceptionMessage : String?
)