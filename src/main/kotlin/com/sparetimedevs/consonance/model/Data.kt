package com.sparetimedevs.consonance.model

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class Score @BsonCreator constructor(
    @BsonId val id: ObjectId = ObjectId(),
    @BsonProperty("title") val title: String,
    @BsonProperty("composerId") val composerId: String,
    @BsonProperty("language") val language: String,
    @BsonProperty("topic") val topic: String
)

data class Composer @BsonCreator constructor(
    @BsonId val id: ObjectId = ObjectId(),
    @BsonProperty("firstName") val firstName: String,
    @BsonProperty("lastName") val lastName: String
)

data class User @BsonCreator constructor(
    @BsonId val id: ObjectId = ObjectId(),
    @BsonProperty("firstName") val firstName: String,
    @BsonProperty("lastName") val lastName: String,
    @BsonProperty("email") val email: String,
    @BsonProperty("encryptedPassword") val encryptedPassword: String
)