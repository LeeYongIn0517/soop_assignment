package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.data.model.RepoWithoutScore
import com.soop_assignment.app.domain.entity.Languages
import com.soop_assignment.app.domain.entity.User
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: GitHubRepository) {
    operator fun invoke(userName: String): User? {
        val ownerData = repository.getUserInfo(userName)
        val repositories = repository.getUserRepositories(userName)
        if (ownerData != null) {
            return User(
                userImageUrl = ownerData.avatarUrl,
                userName = ownerData.login,
                followers = ownerData.followers,
                following = ownerData.following,
                languages = getLanguage(repositories),
                repositories = repositories?.size ?: 0,
                bio = ownerData.bio ?: ""
            )
        } else {
            return null
        }
    }

    fun getLanguage(repositories: List<RepoWithoutScore>?): Languages {
        val languages = repositories?.map {
            it.language ?: ""
        } ?: emptyList()
        return Languages(languages)
    }
}
