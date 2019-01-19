package com.sparetimedevs.consonance

import com.codahale.metrics.Slf4jReporter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.sparetimedevs.consonance.api.api
import com.sparetimedevs.consonance.api.errorHandling
import com.sparetimedevs.consonance.json.serialization.CustomObjectIdSerializer
import com.sparetimedevs.consonance.model.DataInitialiser
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.metrics.Metrics
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSockets
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import java.util.concurrent.TimeUnit

fun Application.module() {

    val logger = this.log
    Dependency.load()

    install(DefaultHeaders)
    install(CallLogging)
    install(Compression)
    install(WebSockets)
//    install(Metrics) {
//        Slf4jReporter.forRegistry(registry)
//            .outputTo(log)
//            .build()
//            .start(10, TimeUnit.SECONDS)
//    }
    install(Routing) {
        api(
            Dependency.scoreRepository,
            Dependency.composerRepository,
            Dependency.userRepository,
            logger
        )
    }
    install(StatusPages) {
        errorHandling(logger)
    }
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            val module = SimpleModule("CustomObjectIdSerializer")
            module.addSerializer(CustomObjectIdSerializer(ObjectId::class.java))
            registerModule(module)
        }
    }
}

fun main() {
    Dependency.load()
    embeddedServer(Netty, 8080, watchPaths = listOf("AppKt"), module = Application::module).start()
    data()
}

private fun data() {
    GlobalScope.launch {
        val dataInitialiser = DataInitialiser(
            Dependency.userRepository,
            Dependency.scoreRepository,
            Dependency.composerRepository
        )
        dataInitialiser.initData()
    }
}
