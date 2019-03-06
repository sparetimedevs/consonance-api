package com.sparetimedevs.consonance.repository

import arrow.core.Either
import com.sparetimedevs.consonance.model.Composer
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

class ComposerRepository(database: Database) {

    private val collection = getCollection<Composer>(database)

    suspend fun findAll(): Either<Error, List<Composer>> = collection.readAll().toEither()

    suspend fun findOneById(id: ObjectId): Either<Error, Composer> = collection.readOne(id).toEither()

    suspend fun deleteAll(): Either<Error, Boolean> = collection.deleteAll().toEither()

    suspend fun deleteOneById(id: ObjectId): Either<Error, Composer> = collection.deleteOne(id).toEither()

    suspend fun save(user: Composer): Either<Error, Composer> = collection.createOne(user).toEither()

    suspend fun update(id: ObjectId, user: Composer): Either<Error, Composer> = collection.updateOne(id, user).toEither()
}
