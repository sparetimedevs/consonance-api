package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.consonance.model.User
import com.sparetimedevs.consonance.repository.UserRepository
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

const val USERS_PATH = "/users"

fun Routing.apiUsers(repository: UserRepository, log: Logger) {

    get(USERS_PATH) {
        repository.findAll().fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    get("$USERS_PATH$ID_PATH_PARAM") {
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

    post(USERS_PATH) {
        val user = call.receive<User>()
        repository.save(user).fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.Created) }
        )
    }

    put("$USERS_PATH$ID_PATH_PARAM") {
        val user = call.receive<User>()
        repository.update(call.parameters[ID]?.toObjectId()!!, user).fold(
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

    delete("$USERS_PATH$ID_PATH_PARAM") {
        repository.deleteOneById(call.parameters[ID]?.toObjectId()!!).fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.NoContent) }
        )
    }
}
