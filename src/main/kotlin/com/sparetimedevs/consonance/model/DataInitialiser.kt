package com.sparetimedevs.consonance.model

import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import com.sparetimedevs.consonance.repository.ComposerRepository
import com.sparetimedevs.consonance.repository.ScoreRepository
import com.sparetimedevs.consonance.repository.UserRepository
import kotlinx.coroutines.launch

class DataInitialiser(private val users: UserRepository, private val scores: ScoreRepository, private val composers: ComposerRepository) {

    private val log = LoggerFactory.getLogger(DataInitialiser::class.java)

    suspend fun initData(): Unit = coroutineScope<Unit> {
        log.info("Init data: ")

        users.deleteAll()
        composers.deleteAll()
        scores.deleteAll()

        launch {
            users.save(
                User(
                    firstName = "Me",
                    lastName = "Is Cool",
                    email = "me.iscool@stuff.com"
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
