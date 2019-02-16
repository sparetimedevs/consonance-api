package com.sparetimedevs.consonance.routes

import com.sparetimedevs.consonance.model.Composer
import com.sparetimedevs.consonance.repository.ComposerRepository
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
import org.koin.ktor.ext.inject

const val COMPOSERS_PATH = "/composers"

fun Route.composerRoutes() {

    val composerRepository: ComposerRepository by inject()

    get(COMPOSERS_PATH) {
        call.respond(HttpStatusCode.OK, composerRepository.findAll())
    }

    get("$COMPOSERS_PATH$ID_PATH_PARAM") {
        call.respond(HttpStatusCode.OK, composerRepository.findOneById(call.parameters[ID]?.toObjectId()!!))
    }

    post(COMPOSERS_PATH) {
        val composer = call.receive<Composer>()
        if (composerRepository.save(composer)) call.respond(HttpStatusCode.Created)
        else call.respond(HttpStatusCode.NotFound)
    }

    put("$COMPOSERS_PATH$ID_PATH_PARAM") {
        val composer = call.receive<Composer>()
        if (composerRepository.update(call.parameters[ID]?.toObjectId()!!, composer)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound)
    }

    delete("$COMPOSERS_PATH$ID_PATH_PARAM") {
        composerRepository.deleteOneById(call.parameters[ID]?.toObjectId()!!)
        call.respond(HttpStatusCode.NoContent)
    }
}
