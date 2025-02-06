package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.model.SpecificRepo
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) {
    suspend operator fun invoke(userName: String, repo: String): ApiResponse<SpecificRepo> {
        val response = gitHubRepository.getRepository(userName, repo)
        return when (response) {
            is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
            is ApiResponse.Success -> ApiResponse.Success<SpecificRepo>(
                data = SpecificRepo(
                    userImageUrl = response.data.owner.avatarUrl,
                    repositoryName = response.data.name,
                    stars = response.data.stargazersCount,
                    watchers = response.data.watchersCount,
                    forks = response.data.forksCount,
                    description = response.data.description ?: ""
                )
            )
        }
    }
}
