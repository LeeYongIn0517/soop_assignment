package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.entity.RepoWithoutScore
import com.soop_assignment.app.domain.model.User
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: GitHubRepository) : BaseUseCase() {
    private lateinit var user: User

    suspend operator fun invoke(userName: String): ApiResponse<User> {
        val ownerDataResponse = repository.getUserInfo(userName)
        val repositoriesResponse = repository.getUserRepositories(userName)
        return ownerDataResponse.mapResponse { ownerData ->
            repositoriesResponse.mapResponse { repositories ->
                user = User(
                    userImageUrl = ownerData.avatarUrl,
                    userName = ownerData.login,
                    followers = ownerData.followers,
                    following = ownerData.following,
                    languages = getLanguage(repositories),
                    repositories = repositories.size.toLong(),
                    bio = ownerData.bio ?: ""
                )
            }
            user
        }
    }

    fun getLanguage(repositories: List<RepoWithoutScore>?): String {
        val languages = repositories?.map { it.language }?.filter { !it.isNullOrBlank() }?.distinct()
        return languages?.joinToString(", ") ?: ""
    }
}
