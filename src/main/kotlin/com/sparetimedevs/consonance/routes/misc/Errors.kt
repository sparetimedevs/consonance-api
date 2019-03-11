package com.sparetimedevs.consonance.routes.misc

import com.sparetimedevs.suspendmongo.result.Error
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class ServiceUnavailable : Throwable()
class BadRequest : Throwable()
class Unauthorized : Throwable()
class NotFound(message: String) : RuntimeException(message)
class MultipleFound(message: String) : RuntimeException(message)

fun StatusPages.Configuration.errorHandling() {

    exception<ServiceUnavailable> {
        call.respond(HttpStatusCode.ServiceUnavailable)
    }
    exception<BadRequest> {
        call.respond(HttpStatusCode.BadRequest)
    }
    exception<Unauthorized> {
        call.respond(HttpStatusCode.Unauthorized)
    }
    exception<NotFound> {
        call.respond(HttpStatusCode.NotFound)
    }
    exception<MultipleFound> {
        call.respond(HttpStatusCode.BadRequest)
    }
    exception<Throwable> {
        call.respond(HttpStatusCode.InternalServerError)
    }
}

suspend fun respondWithError(call: ApplicationCall, error: Error) {
    logger.warn { "MongoDB error ${error::class.simpleName}: ${error.message}" }
    when (error) {
        is Error.EntityNotFound -> call.respond(HttpStatusCode.NotFound)
        is Error.ServiceUnavailable -> call.respond(HttpStatusCode.ServiceUnavailable)
        is Error.UnknownError -> call.respond(HttpStatusCode.InternalServerError)
    }
}
