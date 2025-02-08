package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.model.SpecificRepo
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) : BaseUseCase() {
    suspend operator fun invoke(userName: String, repo: String): ApiResponse<SpecificRepo> {
        val response = gitHubRepository.getRepository(userName, repo)
        return response.mapResponse { repoMetaData, linkHeader ->
            SpecificRepo(
                userImageUrl = repoMetaData.owner.avatarUrl,
                repositoryName = repoMetaData.name,
                stars = repoMetaData.stargazersCount,
                watchers = repoMetaData.watchersCount,
                forks = repoMetaData.forksCount,
                description = repoMetaData.description ?: ""
            )
        }
    }
}
