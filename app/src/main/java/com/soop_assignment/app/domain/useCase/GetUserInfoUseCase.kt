package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.entity.RepoWithoutScore
import com.soop_assignment.app.domain.model.User
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: GitHubRepository) {
    suspend operator fun invoke(userName: String): ApiResponse<User> {
        val ownerDataResponse = repository.getUserInfo(userName)
        val repositoriesResponse = repository.getUserRepositories(userName)
        return when (ownerDataResponse) {
            is ApiResponse.Error -> ApiResponse.Error(ownerDataResponse.code, ownerDataResponse.message)
            is ApiResponse.Exception -> ApiResponse.Exception(ownerDataResponse.exception)
            is ApiResponse.Success -> {
                when (repositoriesResponse) {
                    is ApiResponse.Error -> {
                        ApiResponse.Error(repositoriesResponse.code, repositoriesResponse.message)
                    }

                    is ApiResponse.Exception -> {
                        ApiResponse.Exception(repositoriesResponse.exception)
                    }

                    is ApiResponse.Success -> {
                        ApiResponse.Success(
                            data = User(
                                userImageUrl = ownerDataResponse.data.avatarUrl,
                                userName = ownerDataResponse.data.login,
                                followers = ownerDataResponse.data.followers,
                                following = ownerDataResponse.data.following,
                                languages = getLanguage(repositoriesResponse.data),
                                repositories = repositoriesResponse.data.size.toLong(),
                                bio = ownerDataResponse.data.bio ?: ""
                            )
                        )
                    }
                }
            }
        }
    }

    fun getLanguage(repositories: List<RepoWithoutScore>?): String {
        val languages = repositories?.map {
            it.language ?: ""
        }
        return languages?.distinct()?.joinToString(", ") ?: ""
    }
}
