package com.soop_assignment.app.domain.model

data class User(
    val userImageUrl: String,
    val userName: String,
    val followers: Int,
    val following: Int,
    val languages: Languages,
    val repositories: Int,
    val bio: String
)
