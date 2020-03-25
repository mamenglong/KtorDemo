package com.mml.ktar

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object SimpleJWT{
    private const val secret: String = "menglong"
    private const val issuer = "ktor.io"
    private val algorithm = Algorithm.HMAC256(secret)
    private const val validityInMs = 3600*1000 * 24*7 // 1 week
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
    fun sign(name: String): String = JWT.create().
        withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("name", name)
        .withExpiresAt(getExpiration())
        .sign(algorithm)
}
