package com.sparetimedevs.consonance.routes.misc

import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

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
