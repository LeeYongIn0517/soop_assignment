package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.entity.RepoWithoutScore
import com.soop_assignment.app.domain.extractNextKey
import com.soop_assignment.app.domain.model.ErrorMessage
import com.soop_assignment.app.domain.model.RepositoryCountsAndLanguage
import com.soop_assignment.app.domain.repository.GitHubRepository
import javax.inject.Inject

class GetRepositoryAndLanguageUseCase @Inject constructor(private val repository: GitHubRepository) : BaseUseCase() {

    suspend operator fun invoke(userName: String): ApiResponse<RepositoryCountsAndLanguage> {
        var nextPage: Int? = 1
        val repositories = mutableListOf<RepoWithoutScore>()
        var isSuccess = true
        var error: ErrorMessage? = null
        var throwable: Throwable? = null

        while (nextPage != null) {
            val response = repository.getUserRepositories(userName, nextPage)

            when (response) {
                is ApiResponse.Success -> {
                    if (response.linkHeader != null) {
                        nextPage = extractNextKey(response.linkHeader)
                        repositories.addAll(response.data)
                    } else {
                        nextPage = null
                    }
                }

                is ApiResponse.Error -> {
                    error = ErrorMessage(code = response.code, message = response.message)
                }

                is ApiResponse.Exception -> {
                    throwable = Exception(response.exception)
                }
            }
        }

        return if (isSuccess) {
            ApiResponse.Success(
                RepositoryCountsAndLanguage(
                    languages = getLanguage(repositories),
                    repositoryCounts = repositories.size.toLong()
                ), null
            )
        } else if (error != null) {
            ApiResponse.Error(code = error.code, message = error.message)
        } else {
            ApiResponse.Exception(throwable!!)
        }
    }


    fun getLanguage(repositories: List<RepoWithoutScore>?): String {
        val languages = repositories?.map { it.language }?.filter { !it.isNullOrBlank() }?.distinct()
        return languages?.joinToString(", ") ?: ""
    }
}
