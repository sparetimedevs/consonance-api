package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.consonance.model.Score
import com.sparetimedevs.consonance.repository.ScoreRepository
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
        call.respond(HttpStatusCode.OK, repository.findAll())
    }

    get("$SCORES_PATH$ID_PATH_PARAM") {
        call.respond(HttpStatusCode.OK, repository.findOneById(call.parameters[ID]?.toObjectId()!!))
    }

    post(SCORES_PATH) {
        val score = call.receive<Score>()
        if (repository.save(score)) call.respond(HttpStatusCode.Created)
        else call.respond(HttpStatusCode.NotFound)
    }

    put("$SCORES_PATH$ID_PATH_PARAM") {
        val score = call.receive<Score>()
        if (repository.update(call.parameters[ID]?.toObjectId()!!, score)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound)
    }

    delete("$SCORES_PATH$ID_PATH_PARAM") {
        repository.deleteOneById(call.parameters[ID]?.toObjectId()!!)
        call.respond(HttpStatusCode.NoContent)
    }
}
