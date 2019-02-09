package com.sparetimedevs.consonance.repository

import com.sparetimedevs.consonance.model.User
import com.sparetimedevs.suspendmongo.Database
import com.sparetimedevs.suspendmongo.deleteAll
import com.sparetimedevs.suspendmongo.deleteOneById
import com.sparetimedevs.suspendmongo.getAll
import com.sparetimedevs.suspendmongo.getCollection
import com.sparetimedevs.suspendmongo.getOneById
import com.sparetimedevs.suspendmongo.save
import com.sparetimedevs.suspendmongo.update
import org.bson.types.ObjectId

class UserRepository(database: Database) {

    private val collection = getCollection<User>(database)

    suspend fun findAll() = collection.getAll()

    suspend fun findOneById(id: ObjectId): User = collection.getOneById(id)

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOneById(id)

    suspend fun save(user: User) = collection.save(user)

    suspend fun update(id: ObjectId, user: User) = collection.update(id, user)
}
