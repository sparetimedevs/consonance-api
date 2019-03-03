package com.sparetimedevs.consonance.repository

import com.sparetimedevs.consonance.model.Score
import com.sparetimedevs.suspendmongo.Database
import com.sparetimedevs.suspendmongo.crud.createOne
import com.sparetimedevs.suspendmongo.crud.deleteAll
import com.sparetimedevs.suspendmongo.crud.deleteOne
import com.sparetimedevs.suspendmongo.crud.readAll
import com.sparetimedevs.suspendmongo.crud.readOne
import com.sparetimedevs.suspendmongo.crud.updateOne
import com.sparetimedevs.suspendmongo.getCollection
import org.bson.types.ObjectId

class ScoreRepository(database: Database) {

    private val collection = getCollection<Score>(database)

    suspend fun findAll() = collection.readAll()

    suspend fun findOneById(id: ObjectId) = collection.readOne(id)

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOne(id)

    suspend fun save(score: Score) = collection.createOne(score)

    suspend fun update(id: ObjectId, score: Score) = collection.updateOne(id, score)
}
