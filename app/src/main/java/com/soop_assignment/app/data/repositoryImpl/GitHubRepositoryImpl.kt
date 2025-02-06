package com.soop_assignment.app.data.repositoryImpl

import com.soop_assignment.app.data.service.GitHubApiService
import com.soop_assignment.app.domain.entity.*
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val gitHubApiService: GitHubApiService) : GitHubRepository {
    override suspend fun searchRepositories(query: String): ApiResponse<SearchResponse> {
        return safeApiCall { gitHubApiService.searchRepositories(query) }
    }

    override suspend fun getRepository(owner: String, repo: String): ApiResponse<RepoMetaData> {
        return safeApiCall { gitHubApiService.getRepository(owner, repo) }
    }

    override suspend fun getUserRepositories(username: String): ApiResponse<List<RepoWithoutScore>> {
        return safeApiCall { gitHubApiService.getUserRepositories(username) }
    }

    override suspend fun getUserInfo(username: String): ApiResponse<OwnerAllData> {
        return safeApiCall { gitHubApiService.getUserInfo(username) }
    }
}
