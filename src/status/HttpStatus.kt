package com.mml.ktar.status

import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.response.respond

fun StatusPages.Configuration.installCustom(){
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
        call.respond(mapOf(Pair("code" , it.value),Pair("msg" , it.description)))
    }
}