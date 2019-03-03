package com.sparetimedevs.consonance.repository

import arrow.core.Either
import com.sparetimedevs.consonance.model.User
import com.sparetimedevs.suspendmongo.Database
import com.sparetimedevs.suspendmongo.crud.createOne
import com.sparetimedevs.suspendmongo.crud.deleteAll
import com.sparetimedevs.suspendmongo.crud.deleteOne
import com.sparetimedevs.suspendmongo.crud.readAll
import com.sparetimedevs.suspendmongo.crud.readOne
import com.sparetimedevs.suspendmongo.crud.updateOne
import com.sparetimedevs.suspendmongo.getCollection
import com.sparetimedevs.suspendmongo.result.Error
import com.sparetimedevs.suspendmongo.result.Result
import org.bson.types.ObjectId

class UserRepository(database: Database) {

    private val collection = getCollection<User>(database)

    suspend fun findAll() = collection.readAll()

    suspend fun findOneById(id: ObjectId): Either<Error, User> {
        val result = collection.readOne(id)
        return when (result) {
            is Result.Failure -> Either.Left(result.value)
            is Result.Success -> Either.Right(result.value)
        }
    }

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOne(id)

    suspend fun save(user: User) = collection.createOne(user)

    suspend fun update(id: ObjectId, user: User) = collection.updateOne(id, user)
}
