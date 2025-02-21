package com.soop_assignment.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soop_assignment.app.data.entity.ApiResponse
import com.soop_assignment.app.domain.model.ErrorMessage
import com.soop_assignment.app.presentation.contract.UiEffect
import com.soop_assignment.app.presentation.contract.UiEvent
import com.soop_assignment.app.presentation.contract.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effects: Channel<Effect> = Channel()
    val effects = _effects.receiveAsFlow()

    init {
        subscribeEvents()
    }

    protected fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    protected fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    protected fun setState(reduce: State.() -> State) {
        _uiState.update { it.reduce() }
    }

    protected fun setEffect(vararg builder: Effect) {
        viewModelScope.launch {
            for (effectValue in builder) {
                _effects.send(effectValue)
            }
        }
    }

    protected fun <T> handleError(
        apiResponse: ApiResponse<T>,
        reduce: State.(isLoading: Boolean, isError: Boolean, errorMessage: ErrorMessage?, data: T?) -> State
    ) {
        val UNKNOWN_ERROR_CODE = 500
        val UNKNOWN_ERROR_MESSAGE = "원인을 알 수 없는 오류입니다:("

        when (apiResponse) {
            is ApiResponse.Error -> _uiState.update {
                it.reduce(
                    false,
                    true,
                    ErrorMessage(
                        apiResponse.code,
                        apiResponse.message
                    ),
                    null
                )
            }

            is ApiResponse.Exception -> _uiState.update {
                it.reduce(
                    false,
                    true,
                    ErrorMessage(
                        UNKNOWN_ERROR_CODE,
                        UNKNOWN_ERROR_MESSAGE,
                    ),
                    null
                )
            }

            is ApiResponse.Success -> _uiState.update { it.reduce(false, false, null, apiResponse.data) } //성공
        }
    }
}
