package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.domain.entity.ApiResponse

open class BaseUseCase {
    protected fun <T, R> ApiResponse<T>.mapResponse(
        transform: (T) -> R
    ): ApiResponse<R> {
        return when (this) {
            is ApiResponse.Error -> ApiResponse.Error(this.code, this.message)
            is ApiResponse.Exception -> ApiResponse.Exception(this.exception)
            is ApiResponse.Success -> ApiResponse.Success(transform(this.data))
        }
    }
}
