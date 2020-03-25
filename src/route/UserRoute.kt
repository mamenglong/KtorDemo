package com.mml.ktar.route

import com.mml.ktar.SimpleJWT
import com.mml.ktar.respond.Fail
import com.mml.ktar.respond.RespondResult
import com.mml.ktar.respond.Success
import com.mml.ktar.respond.UserRespond
import database.User
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun Routing.userRoute(simpleJwt: SimpleJWT){
    route("/user"){
        put("/register") {
            val post = call.receive<PostUser>()
            val result = RespondResult<UserRespond>()
            kotlin.runCatching {
                transaction {
                    User.insertAndGetId {
                        it[account] = UUID.randomUUID().toString()
                        it[nickName] = post.nickName
                        it[password] = post.password
                        it[email] = post.email
                    }
                }
            }.onFailure {
                result.code = -1
                result.msg = "${it.message}"
            }.onSuccess {
                if (it.value > 0L) {
                    result.apply {
                        code = 0
                        msg = Success
                    }
                } else {
                    result.apply {
                        msg = Fail
                        code = -2
                    }
                }
            }


            call.respond(result)
        }
        authenticate{
            delete("delete/{id}") {
                val result = RespondResult<UserRespond>()
                val deleteId:Long?  = call.parameters["id"]?.toLong()
                deleteId?.let {
                    val userId  =  transaction {
                        database.User.deleteWhere {
                            User.id eq  it
                        }
                    }
                    if (userId!=0){
                        result.msg="删除$Success"
                    }else{
                        result.code = -1
                        result.msg="删除$Fail"
                    }
                    call.respond(result)
                }?: kotlin.run {
                    result.code = -1
                    result.msg="删除$Fail,参数错误"
                    call.respond(result)
                }
            }
        }
        post("login"){
            val post = call.receive<PostUser>()
            val result = RespondResult<UserRespond>()
            kotlin.runCatching {
                transaction {
                    User.select {
                        ((User.account eq post.account) or (User.email eq post.email)) and  (User.password eq post.password)
                    }
                }
            }.onFailure {
                result.code = -1
                result.msg = "${it.message}"
            }.onSuccess {
                if (it.count() == 1L) {
                    result.apply {
                        code = 0
                        msg = Success
                        val token = simpleJwt.sign("${post.account}${post.password}")
                        data = UserRespond(token)
                    }
                } else {
                    result.apply {
                        msg = Fail
                        code = -2
                    }
                }
            }


            call.respond(result)
        }
    }
}
/*
fun userRouting(routing: Routing, simpleJwt: SimpleJWT){
    routing.apply {
        route("/user") {
            put("/register") {
                val post = call.receive<PostUser>()
                val result = RespondResult<UserRespond>()
                    kotlin.runCatching {
                        transaction {
                            User.insertAndGetId {
                                it[account] = UUID.randomUUID().toString()
                                it[nickName] = post.nickName
                                it[password] = post.password
                                it[email] = post.email
                            }
                        }
                    }.onFailure {
                        result.code = -1
                        result.msg = "${it.message}"
                    }.onSuccess {
                        if (it.value > 0L) {
                            result.apply {
                                code = 0
                                msg = Success
                            }
                        } else {
                            result.apply {
                                msg = Fail
                                code = -2
                            }
                        }
                    }


                call.respond(result)
            }
            authenticate{
                delete("delete/{id}") {
                    val result = RespondResult<UserRespond>()
                    val deleteId:Long?  = call.parameters["id"]?.toLong()
                    deleteId?.let {
                        val userId  =  transaction {
                            database.User.deleteWhere {
                                User.id eq  it
                            }
                        }
                        if (userId!=0){
                            result.msg="删除$Success"
                        }else{
                            result.code = -1
                            result.msg="删除$Fail"
                        }
                        call.respond(result)
                    }?: kotlin.run {
                        result.code = -1
                        result.msg="删除$Fail,参数错误"
                        call.respond(result)
                    }
                }
            }
            post("login"){
                val post = call.receive<PostUser>()
                val result = RespondResult<UserRespond>()
                kotlin.runCatching {
                    transaction {
                        User.select {
                            ((User.account eq post.account) or (User.email eq post.email)) and  (User.password eq post.password)
                        }
                    }
                }.onFailure {
                    result.code = -1
                    result.msg = "${it.message}"
                }.onSuccess {
                    if (it.count() == 1L) {
                        result.apply {
                            code = 0
                            msg = Success
                            val token = simpleJwt.sign("${post.account}${post.password}")
                            data = UserRespond(token)
                        }
                    } else {
                        result.apply {
                            msg = Fail
                            code = -2
                        }
                    }
                }


                call.respond(result)
            }
        }

    }

}*/
