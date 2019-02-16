package com.sparetimedevs.consonance.routes

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title

fun Route.dockerRoutes() {
    get("/") {
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
