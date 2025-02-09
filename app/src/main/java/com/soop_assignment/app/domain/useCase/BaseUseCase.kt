package com.soop_assignment.app.domain.useCase

import com.soop_assignment.app.data.entity.ApiResponse

open class BaseUseCase {
    protected fun <T, R> ApiResponse<T>.mapResponse(
        transform: (T, String?) -> R,
    ): ApiResponse<R> {
        return when (this) {
            is ApiResponse.Error -> ApiResponse.Error(this.code, this.message)
            is ApiResponse.Exception -> ApiResponse.Exception(this.exception)
            is ApiResponse.Success -> ApiResponse.Success(transform(this.data, this.linkHeader), this.linkHeader)
        }
    }
}
