package com.soop_assignment.app.data.repositoryImpl

import com.soop_assignment.app.data.model.Owner
import com.soop_assignment.app.data.model.RepoWithScore
import com.soop_assignment.app.data.model.SearchResponse
import com.soop_assignment.app.data.service.GitHubApiService
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val gitHubApiService: GitHubApiService) : GitHubRepository {
    override fun searchRepositories(query: String): SearchResponse? {
        return gitHubApiService.searchRepositories(query).body()
    }

    override fun getRepository(owner: String, repo: String): RepoWithScore? {
        return gitHubApiService.getRepository(owner, repo).body()
    }

    override fun getUserRepositories(username: String): List<RepoWithScore>? {
        return gitHubApiService.getUserRepositories(username).body()
    }

    override fun getUserInfo(username: String): Owner? {
        return gitHubApiService.getUserInfo(username).body()
    }
}
