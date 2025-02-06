package com.soop_assignment.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.soop_assignment.app.domain.useCase.SearchRepositoriesUseCase
import com.soop_assignment.app.presentation.contract.SearchRepositoryEffect
import com.soop_assignment.app.presentation.contract.SearchRepositoryEvent
import com.soop_assignment.app.presentation.contract.SearchRepositoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepositoriesUseCase: SearchRepositoriesUseCase) :
    BaseViewModel<SearchRepositoryEvent, SearchRepositoryState, SearchRepositoryEffect>() {
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

            is SearchRepositoryEvent.ClickSearchButton -> searchResult(event.text)
            is SearchRepositoryEvent.ChangeSearchWord -> {
                searchResult(event.text)
            }
        }
    }

    private fun searchResult(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (text.isNotBlank()) {
                val result = searchRepositoriesUseCase(text)
                setState { copy(searchResult = result) }
            }
        }
    }
}
