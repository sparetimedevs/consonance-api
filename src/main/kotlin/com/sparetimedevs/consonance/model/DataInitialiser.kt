package com.sparetimedevs.consonance.model

import com.sparetimedevs.consonance.repository.ComposerRepository
import com.sparetimedevs.consonance.repository.ScoreRepository
import com.sparetimedevs.consonance.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

class DataInitialiser(private val users: UserRepository, private val scores: ScoreRepository, private val composers: ComposerRepository) {

    suspend fun initData(): Unit = coroutineScope<Unit> {

        users.deleteAll()
        composers.deleteAll()
        scores.deleteAll()

        launch {
            users.save(
                User(
                    firstName = "Me",
                    lastName = "Is Cool",
                    email = "me.iscool@stuff.com",
                    encryptedPassword = UUID.randomUUID().toString()
                )
            )
        }
        launch {
            composers.save(Composer(firstName = "Roger", lastName = "Waters"))
        }
        launch {
            composers.save(Composer(firstName = "BBBRoger", lastName = "DDDWaters"))
        }
        launch {
            scores.save(
                Score(
                    title = "The leper affinity",
                    composerId = "1",
                    language = "EN",
                    topic = "Metal"
                )
            )
        }
    }
}
