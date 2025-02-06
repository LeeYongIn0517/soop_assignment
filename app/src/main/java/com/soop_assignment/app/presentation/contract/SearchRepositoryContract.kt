package com.soop_assignment.app.presentation.contract

import com.soop_assignment.app.domain.model.BriefRepo
import com.soop_assignment.app.domain.model.ErrorMessage

sealed interface SearchRepositoryEvent : UiEvent {
    data class ClickSearchButton(val text: String) : SearchRepositoryEvent
    data class ChangeSearchWord(val text: String) : SearchRepositoryEvent
    class ClickRepositoryItem(val user: String, val repository: String) : SearchRepositoryEvent
}

data class SearchRepositoryState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: ErrorMessage? = null,
    val searchInput: String = "",
    val searchResult: List<BriefRepo>? = emptyList()
) : UiState

sealed interface SearchRepositoryEffect : UiEffect {
    data class NavigateToRepository(val user: String, val repository: String) : SearchRepositoryEffect
}
