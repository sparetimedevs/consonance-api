package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.consonance.model.Composer
import com.sparetimedevs.consonance.repository.ComposerRepository
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import org.bson.types.ObjectId
import org.slf4j.Logger

const val COMPOSERS_PATH = "/composers"

fun Routing.apiComposers(repository: ComposerRepository, log: Logger) {

    get(COMPOSERS_PATH) {
        call.respond(HttpStatusCode.OK, repository.findAll())
    }

    get("$COMPOSERS_PATH$ID_PATH_PARAM") {
        call.respond(HttpStatusCode.OK, repository.findOneById(call.parameters[ID]?.toObjectId()!!))
    }

    post(COMPOSERS_PATH) {
        val composer = call.receive<Composer>()
        if (repository.save(composer)) call.respond(HttpStatusCode.Created)
        else call.respond(HttpStatusCode.NotFound)
    }

//    put(COMPOSERS_PATH) {
//        val user = call.receive<UserDTO>()
//        val updated = userService.updateUser(user)
//        if (updated == null) call.respond(HttpStatusCode.NotFound)
//        else call.respond(HttpStatusCode.OK, updated)
//    }

    delete("$COMPOSERS_PATH$ID_PATH_PARAM") {
        val removed = repository.deleteOneById(call.parameters[ID]?.toObjectId()!!)//.deleteUser(call.parameters[ID]?.toInt()!!)
        if (!removed.isEmpty()) call.respond(HttpStatusCode.OK) //TODO does this make sense?
        else call.respond(HttpStatusCode.NotFound)
    }
}

fun String.toObjectId(): ObjectId = ObjectId(this)
