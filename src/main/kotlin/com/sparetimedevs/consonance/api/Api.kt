package com.sparetimedevs.consonance.api

import com.sparetimedevs.consonance.api.handlers.apiComposers
import com.sparetimedevs.consonance.api.handlers.apiScores
import com.sparetimedevs.consonance.api.handlers.apiUsers
import com.sparetimedevs.consonance.repository.ComposerRepository
import com.sparetimedevs.consonance.repository.ScoreRepository
import com.sparetimedevs.consonance.repository.UserRepository
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*
import org.slf4j.Logger

fun Routing.api(scoreRepository: ScoreRepository, composerRepository: ComposerRepository, userRepository: UserRepository, logger: Logger) {
    apiScores(scoreRepository, logger)
    apiComposers(composerRepository, logger)
    apiUsers(userRepository, logger)
    apiDocker(logger)
}

fun Routing.apiDocker(logger: Logger) {
    get("/") {
        logger.info("Get at root requested.")
        call.respondHtml {
            head {
                title("Consonance")
            }
            body {
                h1 { +"Machine diagnostics" }
                val runtime = Runtime.getRuntime()
                p { +"Runtime.getRuntime().availableProcessors(): ${runtime.availableProcessors()}" }
                p { +"Runtime.getRuntime().freeMemory(): ${runtime.freeMemory()}" }
                p { +"Runtime.getRuntime().totalMemory(): ${runtime.totalMemory()}" }
                p { +"Runtime.getRuntime().maxMemory(): ${runtime.maxMemory()}" }
                p { +"System.getProperty(\"user.name\"): ${System.getProperty("user.name")}" }
            }
        }
    }
}
