package com.mml.ktar.status

import com.mml.ktar.respond.RespondResult
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.response.respond

fun StatusPages.Configuration.installCustomStatusPages(block:StatusPages.Configuration.()->Unit){
    block()
    status(HttpStatusCode.NotFound) {
        call.respond(
            TextContent(
                "${it.value} ${it.description}",
                ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                it
            )
        )
    }

    status(HttpStatusCode.Unauthorized) {
        val result = RespondResult<Any>()
        result.code = it.value
        result.msg = it.description
        call.respond(result)
    }
}