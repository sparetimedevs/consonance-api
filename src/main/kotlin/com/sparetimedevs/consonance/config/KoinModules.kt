package com.sparetimedevs.consonance.config

import com.sparetimedevs.consonance.model.DataInitialiser
import com.sparetimedevs.consonance.repository.ComposerRepository
import com.sparetimedevs.consonance.repository.ScoreRepository
import com.sparetimedevs.consonance.repository.UserRepository
import com.sparetimedevs.suspendmongo.Database
import org.koin.dsl.module.module

val services = module {
    single { DataInitialiser(get(), get(), get()) }
}


val repositories = module {
    single { Database(MongodbConfiguration.getDbConnectionUrl(), MongodbConfiguration.getDbName()) }
    single { UserRepository(get()) }
    single { ScoreRepository(get()) }
    single { ComposerRepository(get()) }
}
