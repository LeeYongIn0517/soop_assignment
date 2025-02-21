package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.data.entity.ApiResponse
import com.soop_assignment.app.data.entity.RepoWithScore
import com.soop_assignment.app.domain.model.BriefRepo
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) : BaseUseCase() {
    suspend operator fun invoke(query: String, page: Int): ApiResponse<List<BriefRepo>> {
        val response = gitHubRepository.searchRepositories(query = query, page = page)

        return response.mapResponse { data, linkHeader ->
            data.items?.toBriefRepoList() ?: emptyList()
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
