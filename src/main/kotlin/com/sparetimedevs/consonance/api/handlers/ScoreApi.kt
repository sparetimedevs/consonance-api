package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.consonance.model.Score
import com.sparetimedevs.consonance.repository.ScoreRepository
import com.sparetimedevs.suspendmongo.result.Error
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

const val SCORES_PATH = "/scores"

fun Routing.apiScores(repository: ScoreRepository, log: Logger) {

    get(SCORES_PATH) {
        repository.findAll().fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    get("$SCORES_PATH$ID_PATH_PARAM") {
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

    post(SCORES_PATH) {
        val score = call.receive<Score>()
        repository.save(score).fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.Created) }
        )
    }

    put("$SCORES_PATH$ID_PATH_PARAM") {
        val score = call.receive<Score>()
        repository.update(call.parameters[ID]?.toObjectId()!!, score).fold(
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

    delete("$SCORES_PATH$ID_PATH_PARAM") {
        repository.deleteOneById(call.parameters[ID]?.toObjectId()!!).fold(
            { call.respond(HttpStatusCode.InternalServerError) }, //TODO think about wrong flows.
            { call.respond(HttpStatusCode.NoContent) }
        )
    }
}
