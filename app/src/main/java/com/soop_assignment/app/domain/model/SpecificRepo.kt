package com.soop_assignment.app.domain.model

data class SpecificRepo(
    val userImageUrl: String,
    val repositoryName: String,
    val stars: Long,
    val watchers: Long,
    val forks: Long,
    val description: String
)
