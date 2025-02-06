package com.soop_assignment.app.domain.repository

import com.soop_assignment.app.domain.entity.*

interface GitHubRepository {
    suspend fun searchRepositories(query: String): ApiResponse<SearchResponse>
    suspend fun getRepository(owner: String, repo: String): ApiResponse<RepoMetaData>
    suspend fun getUserRepositories(username: String): ApiResponse<List<RepoWithoutScore>>
    suspend fun getUserInfo(username: String): ApiResponse<OwnerAllData>
}
