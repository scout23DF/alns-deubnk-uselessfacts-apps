package de.deutschebank.recruiting.backends.shared.rest.errors

open class GenericBusinessException(message: String) : RuntimeException(message)
class ResourceNotFoundException(message: String) : GenericBusinessException(message)

open class GenericInfrastructureException(message: String) : RuntimeException(message)
class BadRequestException(message: String) : GenericInfrastructureException(message)
