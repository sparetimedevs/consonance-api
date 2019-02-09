package com.sparetimedevs.consonance

import com.sparetimedevs.consonance.config.MongodbConfiguration
import com.sparetimedevs.consonance.repository.ComposerRepository
import com.sparetimedevs.consonance.repository.ScoreRepository
import com.sparetimedevs.consonance.repository.UserRepository
import com.sparetimedevs.suspendmongo.Database
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject

private val serviceModule = module {
    single { Database(MongodbConfiguration.getDbConnectionUrl(), MongodbConfiguration.getDbName()) }
    single { UserRepository(get()) }
    single { ScoreRepository(get()) }
    single { ComposerRepository(get()) }
}

internal object Dependency : KoinComponent {
    fun load() = startKoin(listOf(serviceModule))
    val userRepository by inject<UserRepository>()
    val scoreRepository by inject<ScoreRepository>()
    val composerRepository by inject<ComposerRepository>()
}
