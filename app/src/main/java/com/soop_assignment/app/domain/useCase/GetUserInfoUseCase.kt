package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.User
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: GitHubRepository) {
    operator fun invoke(userName: String): User? {
        val response = repository.getUserInfo(userName)
        if(response != null){
            return User(
                userImageUrl = response.avatarUrl,
                userName = response.login,
                followers = response.
                following = response.followingUrl,
                forks = response.for
            )
        }else{
            return null
        }
    }
}
