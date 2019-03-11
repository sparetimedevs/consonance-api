package com.sparetimedevs.consonance.repository

import arrow.core.Either
import com.sparetimedevs.suspendmongo.result.Error
import com.sparetimedevs.suspendmongo.result.Result

inline fun <reified T : Any> Result<Error, T>.toEither(): Either<Error, T> {
    return when (this) {
        is Result.Failure -> Either.Left(this.value)
        is Result.Success -> Either.Right(this.value)
    }
}
