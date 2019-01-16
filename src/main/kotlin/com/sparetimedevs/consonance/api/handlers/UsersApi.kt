package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.consonance.model.User
import com.sparetimedevs.consonance.repository.UserRepository
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
        call.respond(HttpStatusCode.OK, repository.findAll())
    }

    get("$USERS_PATH$ID_PATH_PARAM") {
        call.respond(HttpStatusCode.OK, repository.findOneById(call.parameters[ID]?.toObjectId()!!))
    }

    post(USERS_PATH) {
        val user = call.receive<User>()
        if (repository.save(user)) call.respond(HttpStatusCode.Created)
        else call.respond(HttpStatusCode.NotFound)
    }

    put("$USERS_PATH$ID_PATH_PARAM") {
        val user = call.receive<User>()
        if (repository.update(call.parameters[ID]?.toObjectId()!!, user)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound)
    }

    delete("$USERS_PATH$ID_PATH_PARAM") {
        repository.deleteOneById(call.parameters[ID]?.toObjectId()!!)
        call.respond(HttpStatusCode.NoContent)
    }
}
