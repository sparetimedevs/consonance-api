package com.sparetimedevs.consonance.repository

import com.mongodb.reactivestreams.client.MongoDatabase
import com.sparetimedevs.consonance.database.deleteAll
import com.sparetimedevs.consonance.database.deleteOneById
import com.sparetimedevs.consonance.database.getAll
import com.sparetimedevs.consonance.database.getCollection
import com.sparetimedevs.consonance.database.getOneById
import com.sparetimedevs.consonance.database.save
import com.sparetimedevs.consonance.model.User
import org.bson.types.ObjectId

class UserRepository(mongoDatabase : MongoDatabase) {

    private val collection = mongoDatabase.getCollection<User>()

    suspend fun findAll() = collection.getAll()

    suspend fun findOneById(id: ObjectId) : User = collection.getOneById(id)

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOneById(id)

    suspend fun save(user: User) = collection.save(user)
}
