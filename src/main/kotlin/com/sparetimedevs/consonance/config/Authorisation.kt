package com.sparetimedevs.consonance.config

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.sparetimedevs.consonance.model.User
import io.ktor.auth.jwt.JWTAuthenticationProvider
import io.ktor.auth.jwt.JWTPrincipal
import java.util.*

data class Token (val token: String)

object JwtConfig {

    const val issuer = "consonance.com"
    private const val secret = "rs9g7s7s87gf"
    private const val validDurationMillis = 36_000_00 * 1 // 1 hour
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()

    fun buildToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.id.toHexString())
        .withClaim("login", user.email)
        .withArrayClaim("name", arrayOf(user.firstName, user.lastName))
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validDurationMillis)
}

fun JWTAuthenticationProvider.customConfigure() {
    verifier(JwtConfig.verifier)
    realm = JwtConfig.issuer
    validate {
        with(it.payload) {
            val login = getClaim("login").isNull
            val id = getClaim("id").isNull
            if (login || id)
                null
            else
                JWTPrincipal(it.payload)
        }
    }
}