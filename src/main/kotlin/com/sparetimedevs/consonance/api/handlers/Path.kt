package com.sparetimedevs.consonance.api.handlers

import com.sparetimedevs.suspendmongo.result.Error
import com.sparetimedevs.suspendmongo.result.Result
import org.bson.types.ObjectId

const val ID = "id"
const val ID_PATH_PARAM = "/{$ID}"

fun String.toObjectId(): ObjectId = ObjectId(this)

inline fun <E: Error, T, C> Result<E, T>.fold(error: (Error) -> C, t: (T) -> C): C = when(this) {

    is Result.Failure -> error(this.value)

    is Result.Success -> t(this.value)
}
