package com.sparetimedevs.consonance.api.handlers

import org.bson.types.ObjectId

const val ID = "id"
const val ID_PATH_PARAM = "/{$ID}"

fun String.toObjectId(): ObjectId = ObjectId(this)
