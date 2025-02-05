package com.soop_assignment.app.domain.model

data class SpecificRepo(
    val userImageUrl: String,
    val repositoryName: String,
    val stars: Int,
    val watchers: Int,
    val forks: Int,
    val description: String
)
