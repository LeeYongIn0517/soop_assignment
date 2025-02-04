package com.soop_assignment.app.data.repositoryImpl

import com.soop_assignment.app.data.model.GitHubOwner
import com.soop_assignment.app.data.model.GitHubRepo
import com.soop_assignment.app.data.model.GitHubSearchResponse
import com.soop_assignment.app.data.service.GitHubApiService
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val gitHubApiService: GitHubApiService):GitHubRepository {
    override suspend fun searchRepositories(query: String): GitHubSearchResponse? {
        return gitHubApiService.searchRepositories(query).body()
    }

    override suspend fun getRepository(owner: String, repo: String): GitHubRepo? {
        return gitHubApiService.getRepository(owner, repo).body()
    }

    override suspend fun getUserRepositories(username: String): List<GitHubRepo>? {
        return gitHubApiService.getUserRepositories(username).body()
    }

    override suspend fun getUserInfo(username: String): GitHubOwner? {
        return gitHubApiService.getUserInfo(username).body()
    }
}
