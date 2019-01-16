package com.sparetimedevs.consonance.repository

import com.mongodb.reactivestreams.client.MongoDatabase
import com.sparetimedevs.consonance.database.deleteAll
import com.sparetimedevs.consonance.database.deleteOneById
import com.sparetimedevs.consonance.database.getAll
import com.sparetimedevs.consonance.database.getCollection
import com.sparetimedevs.consonance.database.getOneById
import com.sparetimedevs.consonance.database.save
import com.sparetimedevs.consonance.database.update
import com.sparetimedevs.consonance.model.Score
import org.bson.types.ObjectId

class ScoreRepository(mongoDatabase: MongoDatabase) {

    private val collection = mongoDatabase.getCollection<Score>()

    suspend fun findAll() = collection.getAll()

    suspend fun findOneById(id: ObjectId) : Score = collection.getOneById(id)

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOneById(id)

    suspend fun save(score: Score) = collection.save(score)

    suspend fun update(id: ObjectId, score: Score) = collection.update(id, score)
}
