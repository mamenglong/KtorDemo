package com.mml.ktar

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

open class SimpleJWT(val secret: String) {
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier = JWT.require(algorithm).build()
    fun sign(name: String): String = JWT.create().withClaim("name", name).sign(algorithm)
}

class User(val account: String, val password: String,val email:String)

val users = Collections.synchronizedMap(
    listOf(User("test", "test","test"))
        .associateBy { it.account }
        .toMutableMap()
)

class InvalidCredentialsException(message: String) : RuntimeException(message)

class TokenVerificationException(message: String) : RuntimeException(message)

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
