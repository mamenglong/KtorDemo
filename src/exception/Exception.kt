package com.mml.ktar.exception

import exception.AuthenticationException
import exception.AuthorizationException
import exception.InvalidCredentialsException
import exception.TokenVerificationException
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

fun StatusPages.Configuration.installCustomException(block:StatusPages.Configuration.()->Unit){
    block()
    exception<AuthenticationException> { cause ->
        call.respond(HttpStatusCode.Unauthorized)
    }
    exception<AuthorizationException> { cause ->
        call.respond(HttpStatusCode.Forbidden)
    }
    exception<InvalidCredentialsException> { exception ->
        call.respond(HttpStatusCode.Unauthorized, mapOf("OK" to false, "error" to (exception.message ?: "")))
    }
    exception<TokenVerificationException> { exception ->
        call.respond(HttpStatusCode.Unauthorized, mapOf("OK" to false, "error" to (exception.message ?: "")))
    }
}