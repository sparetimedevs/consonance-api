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
import com.sparetimedevs.suspendmongo.result.fold

import org.bson.types.ObjectId

class ComposerRepository(database: Database) {

    private val collection = getCollection<Composer>(database)

    suspend fun findAll() = collection.readAll()

    suspend fun findOneById(id: ObjectId): Either<Error, Composer> =
        collection.readOne(id).fold (
            { Either.Left(it) },
            { Either.Right(it) }
        )

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOne(id)

    suspend fun save(composer: Composer) = collection.createOne(composer)

    suspend fun update(id: ObjectId, composer: Composer) = collection.updateOne(id, composer)
}
