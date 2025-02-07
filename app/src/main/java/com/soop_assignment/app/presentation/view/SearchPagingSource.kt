package com.soop_assignment.app.presentation.view

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.model.BriefRepo
import com.soop_assignment.app.domain.useCase.SearchRepositoriesUseCase

class SearchPagingSource(val searchRepositoryUseCase: SearchRepositoriesUseCase, val query: String) :
    PagingSource<Int, BriefRepo>() {

    override fun getRefreshKey(state: PagingState<Int, BriefRepo>): Int? {
        return state.anchorPosition
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BriefRepo> {
        val page = params.key ?: 1

        try {
            val response = searchRepositoryUseCase(query = query, page = page)
            val data = handleApiResponse(response)
            val (prevKey, nextKey) = calculatePageKeys(page, data)

            return LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun handleApiResponse(response: ApiResponse<List<BriefRepo>>): List<BriefRepo> {
        return when (response) {
            is ApiResponse.Success -> response.data
            is ApiResponse.Error -> throw Exception(response.message)
            is ApiResponse.Exception -> throw Exception(response.exception.message)
        }
    }

    private fun calculatePageKeys(page: Int, data: List<BriefRepo>): Pair<Int?, Int?> {
        val prevKey = if (page > 1) page - 1 else null
        val nextKey = if (page == 100 || data.isEmpty()) null else page + 1 //api 문서 상 page최대값은 100
        return Pair(prevKey, nextKey)
    }
}
