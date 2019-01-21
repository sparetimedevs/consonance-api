package com.sparetimedevs.consonance.database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import com.sparetimedevs.consonance.config.MongodbConfiguration
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import kotlin.reflect.KClass

inline fun <reified T : Any> MongoDatabase.getCollection() : MongoCollection<T> =
    getCollection(getCollectionName(T::class), T::class.java)

fun getCollectionName(clazz: KClass<*>): String = clazz.simpleName!!

class Mongodb {
    private val pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()))
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(MongodbConfiguration.load()))
        .codecRegistry(pojoCodecRegistry)
        .build()
    private val client = MongoClients.create(settings)
    val database = client.getDatabase( MongodbConfiguration.getMongodbDatabaseName())
}
