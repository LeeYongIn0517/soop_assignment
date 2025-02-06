package com.soop_assignment.app.domain.model

data class User(
    val userImageUrl: String,
    val userName: String,
    val followers: Long,
    val following: Long,
    val languages: String,
    val repositories: Long,
    val bio: String
)
