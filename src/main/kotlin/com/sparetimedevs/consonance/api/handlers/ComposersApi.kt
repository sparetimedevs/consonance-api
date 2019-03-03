package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.consonance.model.Composer
import com.sparetimedevs.consonance.repository.ComposerRepository
import com.sparetimedevs.suspendmongo.result.Error
import com.sparetimedevs.suspendmongo.result.fold
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import org.slf4j.Logger

const val COMPOSERS_PATH = "/composers"

fun Routing.apiComposers(repository: ComposerRepository, log: Logger) {

    get(COMPOSERS_PATH) {
        repository.findAll().fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    get("$COMPOSERS_PATH$ID_PATH_PARAM") {
        repository.findOneById(call.parameters[ID]?.toObjectId()!!).fold(
            {
                when (it) {
                    is Error.EntityNotFound -> call.respond(HttpStatusCode.NotFound)
                    is Error.ServiceUnavailable -> call.respond(HttpStatusCode.ServiceUnavailable)
                    is Error.UnknownError -> call.respond(HttpStatusCode.InternalServerError)
                }
            },
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    post(COMPOSERS_PATH) {
        val composer = call.receive<Composer>()
        repository.save(composer).fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.Created) }
        )
    }

    put("$COMPOSERS_PATH$ID_PATH_PARAM") {
        val composer = call.receive<Composer>()
        repository.update(call.parameters[ID]?.toObjectId()!!, composer).fold(
            {
                when (it) {
                    is Error.EntityNotFound -> call.respond(HttpStatusCode.NotFound)
                    is Error.ServiceUnavailable -> call.respond(HttpStatusCode.ServiceUnavailable)
                    is Error.UnknownError -> call.respond(HttpStatusCode.InternalServerError)
                }
            },
            { call.respond(HttpStatusCode.NoContent) }
        )
    }

    delete("$COMPOSERS_PATH$ID_PATH_PARAM") {
        repository.deleteOneById(call.parameters[ID]?.toObjectId()!!).fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.NoContent) }
        )
    }
}
