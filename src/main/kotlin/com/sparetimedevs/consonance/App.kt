package com.sparetimedevs.consonance

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.sparetimedevs.consonance.config.customConfigure
import com.sparetimedevs.consonance.config.repositories
import com.sparetimedevs.consonance.config.services
import com.sparetimedevs.consonance.json.serialization.CustomObjectIdSerializer
import com.sparetimedevs.consonance.model.DataInitialiser
import com.sparetimedevs.consonance.routes.composerRoutes
import com.sparetimedevs.consonance.routes.dockerRoutes
import com.sparetimedevs.consonance.routes.login
import com.sparetimedevs.consonance.routes.misc.errorHandling
import com.sparetimedevs.consonance.routes.scoreRoutes
import com.sparetimedevs.consonance.routes.userRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSockets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin

fun Application.module() {

    initaliseDependencies()

    val dataInitialiser : DataInitialiser by inject()
    launch(Dispatchers.Default) {
        preloadData(dataInitialiser)

    }

    install(DefaultHeaders)
    install(CallLogging)
    install(Compression)
    install(WebSockets)
    install(Authentication) { jwt { customConfigure() } }
    install(Routing) {
        authenticate {
            userRoutes()
            scoreRoutes()
            composerRoutes()
        }
        dockerRoutes()
        login()
    }
    install(StatusPages) {
        errorHandling()
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
    embeddedServer(Netty, 8084, watchPaths = listOf("AppKt"), module = Application::module).start()
}

fun initaliseDependencies() {
    startKoin(listOf(services, repositories))
}

suspend fun preloadData(dataInitialiser: DataInitialiser) {
    dataInitialiser.initData()
}
