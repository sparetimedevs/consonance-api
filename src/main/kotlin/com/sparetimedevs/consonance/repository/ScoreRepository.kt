package com.sparetimedevs.consonance.repository

import arrow.core.Either
import com.sparetimedevs.consonance.model.Score
import com.sparetimedevs.suspendmongo.Database
import com.sparetimedevs.suspendmongo.crud.createOne
import com.sparetimedevs.suspendmongo.crud.deleteAll
import com.sparetimedevs.suspendmongo.crud.deleteOne
import com.sparetimedevs.suspendmongo.crud.readAll
import com.sparetimedevs.suspendmongo.crud.readOne
import com.sparetimedevs.suspendmongo.crud.updateOne
import com.sparetimedevs.suspendmongo.getCollection
import com.sparetimedevs.suspendmongo.result.Error
import org.bson.types.ObjectId

class ScoreRepository(database: Database) {

    private val collection = getCollection<Score>(database)

    suspend fun findAll(): Either<Error, List<Score>> = collection.readAll().toEither()

    suspend fun findOneById(id: ObjectId): Either<Error, Score> = collection.readOne(id).toEither()

    suspend fun findOneByTitle(title: String): Either<Error, Score> =
        collection.readOne("title" to title).toEither()

    suspend fun findOneByLanguageAndTopic(language: String, topic: String): Either<Error, Score> =
        collection.readOne("language" to language, "topic" to topic).toEither()

    suspend fun deleteAll(): Either<Error, Boolean> = collection.deleteAll().toEither()

    suspend fun deleteOneById(id: ObjectId): Either<Error, Score> = collection.deleteOne(id).toEither()

    suspend fun save(user: Score): Either<Error, Score> = collection.createOne(user).toEither()

    suspend fun update(id: ObjectId, user: Score): Either<Error, Score> = collection.updateOne(id, user).toEither()
}
