package com.sparetimedevs.consonance.repository

import com.sparetimedevs.consonance.model.Composer
import com.sparetimedevs.suspendmongo.Database
import com.sparetimedevs.suspendmongo.deleteAll
import com.sparetimedevs.suspendmongo.deleteOneById
import com.sparetimedevs.suspendmongo.getAll
import com.sparetimedevs.suspendmongo.getCollection
import com.sparetimedevs.suspendmongo.getOneById
import com.sparetimedevs.suspendmongo.save
import com.sparetimedevs.suspendmongo.update

import org.bson.types.ObjectId

class ComposerRepository(database: Database) {

    private val collection = getCollection<Composer>(database)

    suspend fun findAll() = collection.getAll()

    suspend fun findOneById(id: ObjectId) = collection.getOneById(id)

    suspend fun deleteAll() = collection.deleteAll()

    suspend fun deleteOneById(id: ObjectId) = collection.deleteOneById(id)

    suspend fun save(composer: Composer) = collection.save(composer)

    suspend fun update(id: ObjectId, composer: Composer) = collection.update(id, composer)
}
