package com.sparetimedevs.consonance.routes

import com.sparetimedevs.consonance.config.JwtConfig
import com.sparetimedevs.consonance.config.Token
import com.sparetimedevs.consonance.repository.UserRepository
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.ktor.ext.inject

fun Route.login() {
    val userRepository: UserRepository by inject()

    post("/login") {
        userRepository.findUserByCredentials(credential = call.receive())?.let {
                user -> call.respond(Token(JwtConfig.buildToken(user)))
        } ?: call.respond(HttpStatusCode.Unauthorized)
    }
}