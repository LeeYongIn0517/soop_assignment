package com.soop_assignment.app.domain.model

data class BriefRepo(
    val id: Long,
    val userImageUrl: String,
    val userName: String,
    val repositoryName: String,
    val description: String,
    val stars: Long,
    val language: String
)
