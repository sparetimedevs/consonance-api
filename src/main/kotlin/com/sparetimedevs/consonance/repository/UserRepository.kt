package com.sparetimedevs.consonance.repository

import arrow.core.Either
import com.sparetimedevs.consonance.model.User
import com.sparetimedevs.consonance.model.UserPasswordCredential
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

class UserRepository(database: Database) {

    private val collection = getCollection<User>(database)

    suspend fun findAll(): Either<Error, List<User>> = collection.readAll().toEither()

    suspend fun findOneById(id: ObjectId): Either<Error, User> = collection.readOne(id).toEither()

    suspend fun deleteAll(): Either<Error, Boolean> = collection.deleteAll().toEither()

    suspend fun deleteOneById(id: ObjectId): Either<Error, User> = collection.deleteOne(id).toEither()

    suspend fun save(user: User): Either<Error, User> = collection.createOne(user).toEither()

    suspend fun update(id: ObjectId, user: User): Either<Error, User> = collection.updateOne(id, user).toEither()

    suspend fun findUserByCredentials(credential: UserPasswordCredential): User? {
        return findAll().fold(
            { null },
            { it.firstOrNull { user -> user.encryptedPassword == credential.password && user.email == credential.email } }
        )
    }
}
