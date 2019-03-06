package com.sparetimedevs.consonance.routes

import com.sparetimedevs.consonance.model.User
import com.sparetimedevs.consonance.repository.UserRepository
import com.sparetimedevs.consonance.routes.misc.ID
import com.sparetimedevs.consonance.routes.misc.ID_PATH_PARAM
import com.sparetimedevs.consonance.routes.misc.respondWithError
import com.sparetimedevs.consonance.routes.misc.toObjectId
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import org.koin.ktor.ext.inject

const val USERS_PATH = "/users"

fun Route.userRoutes() {

    val userRepository: UserRepository by inject()

    get(USERS_PATH) {
        userRepository.findAll().fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    get("$USERS_PATH$ID_PATH_PARAM") {
        userRepository.findOneById(call.parameters[ID]?.toObjectId()!!).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    post(USERS_PATH) {
        val user = call.receive<User>()
        userRepository.save(user).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.Created) }
        )
    }

    put("$USERS_PATH$ID_PATH_PARAM") {
        val user = call.receive<User>()
        userRepository.update(call.parameters[ID]?.toObjectId()!!, user).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.NoContent) }
        )
    }

    delete("$USERS_PATH$ID_PATH_PARAM") {
        userRepository.deleteOneById(call.parameters[ID]?.toObjectId()!!).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.NoContent) }
        )
    }
}
