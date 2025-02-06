package com.soop_assignment.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.soop_assignment.app.domain.entity.ApiResponse
import com.soop_assignment.app.domain.useCase.GetRepositoryUseCase
import com.soop_assignment.app.domain.useCase.GetUserInfoUseCase
import com.soop_assignment.app.presentation.contract.RepositoryEffect
import com.soop_assignment.app.presentation.contract.RepositoryEvent
import com.soop_assignment.app.presentation.contract.RepositoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val getRepositoryUseCase: GetRepositoryUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<RepositoryEvent, RepositoryState, RepositoryEffect>() {

    override fun createInitialState(): RepositoryState {
        return RepositoryState()
    }

    override fun handleEvent(event: RepositoryEvent) {
        when (event) {
            is RepositoryEvent.GetRepository -> {
                getRepository(userName = event.userName, repository = event.repository)
                getUser(event.userName)
            }

            RepositoryEvent.ClickBackButton -> setEffect(RepositoryEffect.NavigateToBack)
            RepositoryEvent.ClickUserMore -> setState { copy(isModalExpanded = !currentState.isModalExpanded) }
        }
    }

    private fun getRepository(userName: String, repository: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repositoryResult = getRepositoryUseCase(userName = userName, repo = repository)
            when (repositoryResult) {
                is ApiResponse.Error -> {}
                is ApiResponse.Exception -> {}
                is ApiResponse.Success -> {
                    setState { copy(isLoading = false, repository = repositoryResult.data) }
                }
            }

        }
    }

    private fun getUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userResult = getUserInfoUseCase(userName = userName)
            when (userResult) {
                is ApiResponse.Error -> {}
                is ApiResponse.Exception -> {}
                is ApiResponse.Success -> {
                    setState { copy(isLoading = false, user = userResult.data) }
                }
            }
        }
    }
}
