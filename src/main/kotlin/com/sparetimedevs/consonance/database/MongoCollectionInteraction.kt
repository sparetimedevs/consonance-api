package com.sparetimedevs.consonance.database

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success.SUCCESS
import com.sparetimedevs.consonance.api.NotFound
import kotlinx.coroutines.channels.toCollection
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.openSubscription
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId

suspend inline fun <T : Document> MongoCollection<T>.getAll(): ArrayList<T> =
    this.find().openSubscription().toCollection(ArrayList())

suspend inline fun <reified T : Document> MongoCollection<T>.getOneById(id: ObjectId): T {
    val filter = idFilterQuery(id)
    return this.find(filter).awaitFirstOrNull() ?: throw NotFound("No ${getCollectionName(T::class)} found, while 1 was expected.")
}

suspend inline fun <T : Document> MongoCollection<T>.deleteAll(): Boolean {
    val publisher = this.drop()
    val operation = publisher.awaitFirstOrNull()
    return operation == SUCCESS
}

suspend inline fun <reified T : Document> MongoCollection<T>.deleteOneById(id: ObjectId): T {
    val filter = idFilterQuery(id)
    val publisher = this.findOneAndDelete(filter)
    return publisher.awaitFirstOrNull() ?: throw NotFound("No ${getCollectionName(T::class)} found, while 1 was expected.")
}

suspend inline fun <T : Document> MongoCollection<T>.save(entity: T): Boolean {
    val publisher = this.insertOne(entity)
    val operation = publisher.awaitFirstOrNull()
    return operation == SUCCESS
}

//TODO update

fun idFilterQuery(id: ObjectId): Bson = Filters.eq("_id", id)
