package de.deutschebank.recruiting.backends.shared.rest.errors

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GlobalExceptionMapper : ExceptionMapper<Throwable> {
    override fun toResponse(exception: Throwable): Response {
        return when (exception) {
            is ResourceNotFoundException -> Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorResponseHolder("Resource not found", exception.message))
                .build()
            is BadRequestException -> Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponseHolder("Bad request", exception.message))
                .build()
            is GenericBusinessException -> Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity(ErrorResponseHolder("[Some business constraint violation happened]", exception.message))
                .build()
            is GenericInfrastructureException -> Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(ErrorResponseHolder("Service unavailable temporarily", exception.message))
                .build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorResponseHolder("Internal server error", exception.message))
                .build()
        }
    }
}

