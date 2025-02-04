package com.soop_assignment.app.domain.entity

data class User(
    val userImageUrl:String,
    val userName:String,
    val followers:Int,
    val following:Int,
    val forks:Int
)
