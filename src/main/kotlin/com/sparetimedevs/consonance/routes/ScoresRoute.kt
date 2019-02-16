package com.sparetimedevs.consonance.routes

import com.sparetimedevs.consonance.model.Score
import com.sparetimedevs.consonance.repository.ScoreRepository
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

const val SCORES_PATH = "/scores"

fun Route.scoreRoutes() {

    val scoreRepository: ScoreRepository by inject()

    get(SCORES_PATH) {
        call.respond(HttpStatusCode.OK, scoreRepository.findAll())
    }

    get("$SCORES_PATH$ID_PATH_PARAM") {
        call.respond(HttpStatusCode.OK, scoreRepository.findOneById(call.parameters[ID]?.toObjectId()!!))
    }

    post(SCORES_PATH) {
        val score = call.receive<Score>()
        if (scoreRepository.save(score)) call.respond(HttpStatusCode.Created)
        else call.respond(HttpStatusCode.NotFound)
    }

    put("$SCORES_PATH$ID_PATH_PARAM") {
        val score = call.receive<Score>()
        if (scoreRepository.update(call.parameters[ID]?.toObjectId()!!, score)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound)
    }

    delete("$SCORES_PATH$ID_PATH_PARAM") {
        scoreRepository.deleteOneById(call.parameters[ID]?.toObjectId()!!)
        call.respond(HttpStatusCode.NoContent)
    }
}
