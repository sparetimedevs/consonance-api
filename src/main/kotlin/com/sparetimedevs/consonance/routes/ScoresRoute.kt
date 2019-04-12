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
const val TITLE = "title"
const val TITLE_PATH_PARAM = "/{$TITLE}"
const val LANGUAGE = "language"
const val LANGUAGE_PATH_PARAM = "/{$LANGUAGE}"
const val TOPIC = "topic"
const val TOPIC_PATH_PARAM = "/{$TOPIC}"

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

    //TODO this one seems to collide with findOneById. http://localhost:8084/scores/Theleperaffinity vs http://localhost:8084/scores/5cb0e874200d8e031303bb98
    get("$SCORES_PATH$TITLE_PATH_PARAM") {
        scoreRepository.findOneByTitle(call.parameters[TITLE]!!).fold(
            { respondWithError(call, it) },
            { call.respond(HttpStatusCode.OK, it) }
        )
    }

    get("$SCORES_PATH$LANGUAGE_PATH_PARAM$TOPIC_PATH_PARAM") {
        scoreRepository.findOneByLanguageAndTopic(call.parameters[LANGUAGE]!!, call.parameters[TOPIC]!!).fold(
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
