package com.soop_assignment.app.presentation.contract

import com.soop_assignment.app.domain.model.ErrorMessage
import com.soop_assignment.app.domain.model.RepositoryCountsAndLanguage
import com.soop_assignment.app.domain.model.SpecificRepo
import com.soop_assignment.app.domain.model.User

sealed interface RepositoryEvent : UiEvent {
    data class GetRepository(val userName: String, val repository: String) : RepositoryEvent
    data object ClickBackButton : RepositoryEvent
    data object ClickUserMore : RepositoryEvent
}

data class RepositoryState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: ErrorMessage? = null,
    val repository: SpecificRepo? = null,
    val user: User? = null,
    val repositoryAndLanguage: RepositoryCountsAndLanguage? = null,
    val isModalExpanded: Boolean = false
) : UiState

sealed interface RepositoryEffect : UiEffect {
    data object NavigateToBack : RepositoryEffect
}
