package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.model.User
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: GitHubRepository) : BaseUseCase() {
    suspend operator fun invoke(userName: String): ApiResponse<User> {
        val ownerDataResponse = repository.getUserInfo(userName)
        return ownerDataResponse.mapResponse { ownerData ->
            User(
                userImageUrl = ownerData.avatarUrl,
                userName = ownerData.login,
                followers = ownerData.followers,
                following = ownerData.following,
                bio = ownerData.bio ?: ""
            )
        }
    }
}
