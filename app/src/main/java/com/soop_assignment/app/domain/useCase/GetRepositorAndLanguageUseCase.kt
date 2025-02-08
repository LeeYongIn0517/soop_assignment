package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.entity.RepoWithoutScore
import com.soop_assignment.app.domain.extractLastKey
import com.soop_assignment.app.domain.model.ErrorMessage
import com.soop_assignment.app.domain.model.RepositoryCountsAndLanguage
import com.soop_assignment.app.domain.repository.GitHubRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetRepositoryAndLanguageUseCase @Inject constructor(private val repository: GitHubRepository) : BaseUseCase() {

    suspend operator fun invoke(userName: String): ApiResponse<RepositoryCountsAndLanguage> {
        var isSuccess = false
        var error: ErrorMessage? = null
        var throwable: Throwable? = null
        val repositories = mutableListOf<RepoWithoutScore>()

        coroutineScope {
            val firstResponse = repository.getUserRepositories(userName, 1)

            val lastPage = when (firstResponse) {
                is ApiResponse.Success -> {
                    isSuccess = true
                    repositories.addAll(firstResponse.data)
                    extractLastKey(firstResponse.linkHeader ?: "")
                }

                is ApiResponse.Error -> {
                    error = ErrorMessage(code = firstResponse.code, message = firstResponse.message)
                    null
                }

                is ApiResponse.Exception -> {
                    throwable = Exception(firstResponse.exception)
                    null
                }
            }

            if (lastPage != null) {
                (2..lastPage).chunked(5).forEach { batch ->
                    val responses = batch.map { page ->
                        async {
                            delay(500)
                            repository.getUserRepositories(userName, page)
                        }
                    }.awaitAll()

                    responses.forEach { response ->
                        //중간 응답이 실패하더라도, 일단 그 다음 응답이 성공할 경우 레포지토리 수를 계산하도록 함
                        when (response) {
                            is ApiResponse.Success -> {
                                repositories.addAll(response.data)
                                isSuccess = true
                            }

                            is ApiResponse.Error -> {
                                error = ErrorMessage(response.code, response.message)
                            }

                            is ApiResponse.Exception -> {
                                throwable = Exception(response.exception)
                            }
                        }
                    }
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
            ApiResponse.Error(code = error?.code ?: 500, message = error?.message ?: "예기치 못한 오류가 발생했습니다:(")
        } else {
            ApiResponse.Exception(throwable ?: Throwable())
        }
    }

    fun getLanguage(repositories: List<RepoWithoutScore>?): String {
        val languages = repositories?.map { it.language }?.filter { !it.isNullOrBlank() }?.distinct()
        return languages?.joinToString(", ") ?: ""
    }
}
