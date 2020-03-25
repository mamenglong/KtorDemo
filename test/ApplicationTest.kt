package com.mml.ktar

import com.google.gson.Gson
import com.mml.ktar.route.PostUser
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.netty.handler.codec.http.HttpHeaders.addHeader
import kotlinx.css.body

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
    @Test
    fun testUserRegister() {
        withTestApplication({ module(testing = true) }) {
            var user= PostUser("menglong","menglong","menglong","")
            (0..1000).forEach {
                user=PostUser("menglong$it","menglong$it","menglong","$it")
                with(handleRequest(HttpMethod.Put, "/user/register"){
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    setBody(Gson().toJson(user))
                }) {
                   println(response.content)
                }
            }

        }
    }
    @Test
    fun testUserLogin() {
        withTestApplication({ module(testing = true) }) {
            var user= PostUser("menglong","menglong","menglong","")
            (0..1000).forEach {
                user=PostUser("menglong$it","menglong$it","menglong","$it")
                with(handleRequest(HttpMethod.Post, "/user/login"){
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    setBody(Gson().toJson(user))
                }) {
                    println(response.content)
                }
            }

        }
    }
}
