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
import io.ktor.routing.put
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

    put("$COMPOSERS_PATH$ID_PATH_PARAM") {
        val composer = call.receive<Composer>()
        if (repository.update(call.parameters[ID]?.toObjectId()!!, composer)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound)
    }

    delete("$COMPOSERS_PATH$ID_PATH_PARAM") {
        repository.deleteOneById(call.parameters[ID]?.toObjectId()!!)
        call.respond(HttpStatusCode.NoContent)
    }
}
