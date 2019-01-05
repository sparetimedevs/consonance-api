package com.sparetimedevs.consonance.database

import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import com.sparetimedevs.consonance.config.MongodbConfiguration
import kotlin.reflect.KClass

inline fun <reified T : Any> MongoDatabase.getCollection() : MongoCollection<T> =
    getCollection(getCollectionName(T::class), T::class.java)

fun getCollectionName(clazz: KClass<*>): String = clazz.simpleName!!

class Mongodb {
    private val client = MongoClients.create(MongodbConfiguration.load())
    val database = client.getDatabase("consonance")
}
