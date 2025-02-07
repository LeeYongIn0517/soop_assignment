package com.soop_assignment.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.soop_assignment.app.domain.model.BriefRepo
import com.soop_assignment.app.domain.useCase.SearchRepositoriesUseCase
import com.soop_assignment.app.presentation.contract.SearchRepositoryEffect
import com.soop_assignment.app.presentation.contract.SearchRepositoryEvent
import com.soop_assignment.app.presentation.contract.SearchRepositoryState
import com.soop_assignment.app.presentation.view.SearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepositoriesUseCase: SearchRepositoriesUseCase) :
    BaseViewModel<SearchRepositoryEvent, SearchRepositoryState, SearchRepositoryEffect>() {
    val PAGE_SIZE = 30

    override fun createInitialState(): SearchRepositoryState {
        return SearchRepositoryState()
    }

    override fun handleEvent(event: SearchRepositoryEvent) {
        when (event) {
            is SearchRepositoryEvent.ClickRepositoryItem -> setEffect(
                SearchRepositoryEffect.NavigateToRepository(
                    event.user,
                    event.repository
                )
            )

            is SearchRepositoryEvent.ClickSearchButton -> {
                searchResult(event.text)
            }

            is SearchRepositoryEvent.ChangeSearchWord -> {
                setState { copy(searchInput = event.text) }
            }
        }
    }

    fun searchResult(query: String) =
        SearchPagingSource(searchRepositoriesUseCase, query, onErrorOcurred = { setState { copy(errorMessage = it) } })

    fun getSearchPagingResult(query: String): Flow<PagingData<BriefRepo>>? {
        return if (query.isNotBlank()) {
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, prefetchDistance = 1),
                pagingSourceFactory = { searchResult(query) }
            ).flow.cachedIn(viewModelScope)
        } else {
            null
        }
    }
}
