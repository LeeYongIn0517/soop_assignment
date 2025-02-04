package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.SpecificRepo
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) {
    operator fun invoke(userName: String, repo: String): SpecificRepo? {
        val response = gitHubRepository.getRepository(userName, repo)
        if (response != null) {
            return SpecificRepo(
                userImageUrl = response.owner.avatarUrl,
                repositoryName = response.name,
                stars = response.stargazersCount,
                watchers = response.watchersCount,
                forks = response.forksCount,
                description = response.description ?: ""
            )
        } else {
            return null
        }
    }
}
