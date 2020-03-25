package com.mml.ktar.respond

const val Success = "success"
const val Fail = "fail"
data class RespondResult<T>(
    var msg:String = "success",
    var code:Int = 0,
    var data:T? = null
)
