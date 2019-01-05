package com.sparetimedevs.consonance.api

import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.error
import org.slf4j.Logger

class ServiceUnavailable : Throwable()
class BadRequest : Throwable()
class Unauthorized : Throwable()
class NotFound(message: String) : RuntimeException(message)
class MultipleFound(message: String) : RuntimeException(message)

fun StatusPages.Configuration.errorHandling(log: Logger) {

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
    exception<Throwable> { cause ->
        log.error(cause)
        call.respond(HttpStatusCode.InternalServerError)
    }
}
