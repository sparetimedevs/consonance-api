package com.sparetimedevs.consonance

import com.sparetimedevs.consonance.database.Mongodb
import com.sparetimedevs.consonance.repository.ComposerRepository
import com.sparetimedevs.consonance.repository.ScoreRepository
import com.sparetimedevs.consonance.repository.UserRepository
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject

private val serviceModule = module {
    //    single { FooImpl() as Foo }
    single { Mongodb() }
    single { UserRepository(get<Mongodb>().database) }
    single { ScoreRepository(get<Mongodb>().database) }
    single { ComposerRepository(get<Mongodb>().database) }
}

internal object Dependency : KoinComponent {
    fun load() = startKoin(listOf(serviceModule))

    //    val foo by inject<Foo>()
    val mongodb by inject<Mongodb>()
    val userRepository by inject<UserRepository>()
    val scoreRepository by inject<ScoreRepository>()
    val composerRepository by inject<ComposerRepository>()
}
