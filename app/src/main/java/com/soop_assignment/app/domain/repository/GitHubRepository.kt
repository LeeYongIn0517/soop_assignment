package com.soop_assignment.app.domain.repository

import com.soop_assignment.app.data.model.GitHubOwner
import com.soop_assignment.app.data.model.GitHubRepo
import com.soop_assignment.app.data.model.GitHubSearchResponse

interface GitHubRepository {
    suspend fun searchRepositories(query: String): GitHubSearchResponse?
    suspend fun getRepository(owner: String, repo: String): GitHubRepo?
    suspend fun getUserRepositories(username: String): List<GitHubRepo>?
    suspend fun getUserInfo(username: String): GitHubOwner?
}
