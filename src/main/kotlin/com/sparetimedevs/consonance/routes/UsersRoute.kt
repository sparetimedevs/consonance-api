package com.sparetimedevs.consonance.routes

import com.sparetimedevs.consonance.model.User
import com.sparetimedevs.consonance.repository.UserRepository
import com.sparetimedevs.consonance.routes.misc.ID
import com.sparetimedevs.consonance.routes.misc.ID_PATH_PARAM
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
import mu.KotlinLogging
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger {}

const val USERS_PATH = "/users"

fun Route.userRoutes() {

    val userRepository: UserRepository by inject()

    get(USERS_PATH) {
        call.respond(HttpStatusCode.OK, userRepository.findAll())
    }

    get("$USERS_PATH$ID_PATH_PARAM") {
        call.respond(HttpStatusCode.OK, userRepository.findOneById(call.parameters[ID]?.toObjectId()!!))
    }

    post(USERS_PATH) {
        val user = call.receive<User>()
        if (userRepository.save(user)) call.respond(HttpStatusCode.Created)
        else call.respond(HttpStatusCode.NotFound)
    }

    put("$USERS_PATH$ID_PATH_PARAM") {
        val user = call.receive<User>()
        if (userRepository.update(call.parameters[ID]?.toObjectId()!!, user)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound)
    }

    delete("$USERS_PATH$ID_PATH_PARAM") {
        userRepository.deleteOneById(call.parameters[ID]?.toObjectId()!!)
        call.respond(HttpStatusCode.NoContent)
    }
}
