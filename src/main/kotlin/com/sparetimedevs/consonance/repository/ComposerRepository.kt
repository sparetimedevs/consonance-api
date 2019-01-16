package com.sparetimedevs.consonance.repository

import com.mongodb.reactivestreams.client.MongoDatabase
import com.sparetimedevs.consonance.database.deleteAll
import com.sparetimedevs.consonance.database.deleteOneById
import com.sparetimedevs.consonance.database.getAll
import com.sparetimedevs.consonance.database.getCollection
import com.sparetimedevs.consonance.database.getOneById
import com.sparetimedevs.consonance.database.save
import com.sparetimedevs.consonance.database.update
import com.sparetimedevs.consonance.model.Composer
import org.bson.types.ObjectId

class ComposerRepository(mongoDatabase: MongoDatabase) {

    private val collection = mongoDatabase.getCollection<Composer>()

    suspend fun findAll() = collection.getAll()

    suspend fun findOneById(id: ObjectId) : Composer = collection.getOneById(id)

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOneById(id)

    suspend fun save(composer: Composer) = collection.save(composer)

    suspend fun update(id: ObjectId, composer: Composer) = collection.update(id, composer)
}
