package com.sparetimedevs.consonance.routes

import com.sparetimedevs.consonance.model.Score
import com.sparetimedevs.consonance.repository.ScoreRepository
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

const val SCORES_PATH = "/scores"

fun Route.scoreRoutes() {

    val scoreRepository: ScoreRepository by inject()

    get(SCORES_PATH) {
        scoreRepository.findAll().fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    get("$SCORES_PATH$ID_PATH_PARAM") {
        scoreRepository.findOneById(call.parameters[ID]?.toObjectId()!!).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    post(SCORES_PATH) {
        val score = call.receive<Score>()
        scoreRepository.save(score).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.Created) }
        )
    }

    put("$SCORES_PATH$ID_PATH_PARAM") {
        val score = call.receive<Score>()
        scoreRepository.update(call.parameters[ID]?.toObjectId()!!, score).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.NoContent) }
        )
    }

    delete("$SCORES_PATH$ID_PATH_PARAM") {
        scoreRepository.deleteOneById(call.parameters[ID]?.toObjectId()!!).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.NoContent) }
        )
    }
}
