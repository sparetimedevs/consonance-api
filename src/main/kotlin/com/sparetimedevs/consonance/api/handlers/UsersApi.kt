package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.consonance.repository.UserRepository
import io.ktor.routing.Routing
import org.slf4j.Logger

const val USERS_PATH = "/users"
const val UPDATES_PATH = "/updates"
const val ID = "id"
const val ID_PATH_PARAM = "/{$ID}"

fun Routing.apiUsers(repository: UserRepository, log: Logger) {

    //TODO write implementation
}
