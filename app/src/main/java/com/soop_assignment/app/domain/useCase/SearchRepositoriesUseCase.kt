package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.entity.RepoWithScore
import com.soop_assignment.app.domain.model.BriefRepo
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) {
    suspend operator fun invoke(query: String, page: Int): ApiResponse<List<BriefRepo>> {
        val response = gitHubRepository.searchRepositories(query = query, page = page)
        return when (response) {
            is ApiResponse.Error -> {
                ApiResponse.Error(response.code, response.message)
            }

            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data.items?.toBriefRepoList() ?: emptyList())
            }
        }
    }

    private fun List<RepoWithScore>.toBriefRepoList(): List<BriefRepo> {
        return this.map {
            BriefRepo(
                id = it.id,
                userImageUrl = it.owner.avatarUrl,
                userName = it.owner.login,
                repositoryName = it.name,
                description = it.description ?: "",
                stars = it.stargazersCount,
                language = it.language ?: ""
            )
        }
    }
}
