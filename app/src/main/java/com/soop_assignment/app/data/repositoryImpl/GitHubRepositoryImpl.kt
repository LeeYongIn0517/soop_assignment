package com.soop_assignment.app.data.repositoryImpl

import com.soop_assignment.app.data.model.OwnerAllData
import com.soop_assignment.app.data.model.RepoMetaData
import com.soop_assignment.app.data.model.RepoWithoutScore
import com.soop_assignment.app.data.model.SearchResponse
import com.soop_assignment.app.data.service.GitHubApiService
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val gitHubApiService: GitHubApiService) : GitHubRepository {
    override fun searchRepositories(query: String): SearchResponse? {
        return gitHubApiService.searchRepositories(query).body()
    }

    override fun getRepository(owner: String, repo: String): RepoMetaData? {
        return gitHubApiService.getRepository(owner, repo).body()
    }

    override fun getUserRepositories(username: String): List<RepoWithoutScore>? {
        return gitHubApiService.getUserRepositories(username).body()
    }

    override fun getUserInfo(username: String): OwnerAllData? {
        return gitHubApiService.getUserInfo(username).body()
    }
}
