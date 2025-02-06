package com.soop_assignment.app.data.repositoryImpl

import com.soop_assignment.app.data.entity.OwnerAllData
import com.soop_assignment.app.data.entity.RepoMetaData
import com.soop_assignment.app.data.entity.RepoWithoutScore
import com.soop_assignment.app.data.entity.SearchResponse
import com.soop_assignment.app.data.service.GitHubApiService
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val gitHubApiService: GitHubApiService) : GitHubRepository {
    override suspend fun searchRepositories(query: String): SearchResponse? {
        return gitHubApiService.searchRepositories(query).execute().body()
    }

    override fun getRepository(owner: String, repo: String): RepoMetaData? {
        return gitHubApiService.getRepository(owner, repo).execute().body()
    }

    override fun getUserRepositories(username: String): List<RepoWithoutScore>? {
        return gitHubApiService.getUserRepositories(username).execute().body()
    }

    override fun getUserInfo(username: String): OwnerAllData? {
        return gitHubApiService.getUserInfo(username).execute().body()
    }
}
