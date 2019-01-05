package com.sparetimedevs.consonance.model

import com.fasterxml.jackson.annotation.JsonGetter
import org.bson.Document
import org.bson.types.ObjectId

data class Version(val id: ObjectId = ObjectId(), val value: String = "v1.0") //Default value just for example purposes.

data class Composer(val id: ObjectId = ObjectId(), val firstName: String, val lastName: String) : Document() {
    init {
        this["_id"] = id
        this.append("firstName", firstName)
        this.append("lastName", lastName)
    }

    //TODO make to json convertion use id.toHexString()
    @JsonGetter("id")
    fun getId() : String {
        return id.toHexString()
    }
}

data class User(val id: ObjectId = ObjectId(), val firstName: String, val lastName: String, val email: String) : Document() {
    init {
        this["_id"] = this.id
        this.append("firstName", this.firstName)
        this.append("lastName", this.lastName)
        this.append("email", this.email)
    }
}

data class Score(val id: ObjectId = ObjectId(), val title: String, val composerId: String, val language: String, val topic: String) : Document() {
    init {
        this["_id"] = this.id
        this.append("composerId", this.composerId)
        this.append("language", this.language)
        this.append("topic", this.topic)
    }
}

//TODO implement Kmongo, and expose these via ktor. Write tests and it should be about equal to current consonance app.
