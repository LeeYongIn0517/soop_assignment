package com.soop_assignment.app.presentation.view

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soop_assignment.app.data.entity.ApiResponse
import com.soop_assignment.app.domain.extractNextKey
import com.soop_assignment.app.domain.model.BriefRepo
import com.soop_assignment.app.domain.model.ErrorMessage
import com.soop_assignment.app.domain.useCase.SearchRepositoriesUseCase

class SearchPagingSource(
    val searchRepositoryUseCase: SearchRepositoriesUseCase,
    val query: String,
    val onErrorOcurred: (error: ErrorMessage) -> Unit
) :
    PagingSource<Int, BriefRepo>() {

    override fun getRefreshKey(state: PagingState<Int, BriefRepo>): Int? {
        return state.anchorPosition
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BriefRepo> {
        val page = params.key ?: 1

        try {
            val response = searchRepositoryUseCase(query = query, page = page)
            val (data, linkHeader) = handleApiResponse(response)
            val (prevKey, nextKey) = calculatePageKeys(page, linkHeader)

            return LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun handleApiResponse(response: ApiResponse<List<BriefRepo>>): Pair<List<BriefRepo>, String> {
        return when (response) {
            is ApiResponse.Success -> {
                if (response.linkHeader != null) {
                    Pair(response.data, response.linkHeader)
                } else {
                    throw Exception("API 오류 발생")
                }
            }

            is ApiResponse.Error -> {
                onErrorOcurred(ErrorMessage(response.code, response.message))
                throw Exception(response.message)
            }

            is ApiResponse.Exception -> throw Exception(response.exception.message)
        }
    }

    private fun calculatePageKeys(page: Int, linkHeader: String): Pair<Int?, Int?> {
        val prevKey = if (page > 1) page - 1 else null
        val nextKey = extractNextKey(linkHeader)
        return Pair(prevKey, nextKey)
    }
}
