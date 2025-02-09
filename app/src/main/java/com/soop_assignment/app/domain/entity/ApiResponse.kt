package com.soop_assignment.app.domain.entity

import com.soop_assignment.app.domain.model.ErrorType
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T, val linkHeader: String?) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
    data class Exception(val exception: Throwable) : ApiResponse<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = apiCall()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResponse.Success(body, response.headers()["Link"])
            } else {
                ApiResponse.Error(response.code(), "Response body is null")
            }
        } else {
            when (response.code()) {
                ErrorType.E301.code -> ApiResponse.Error(response.code(), ErrorType.E301.message)
                ErrorType.E304.code -> ApiResponse.Error(response.code(), ErrorType.E304.message)
                ErrorType.E403.code -> ApiResponse.Error(response.code(), ErrorType.E403.message)

                ErrorType.E404.code -> ApiResponse.Error(response.code(), ErrorType.E404.message)
                ErrorType.E503.code -> ApiResponse.Error(response.code(), ErrorType.E503.message)
                else -> ApiResponse.Error(response.code(), "예기치 않은 오류가 발생했습니다:(")
            }
        }
    } catch (e: HttpException) {
        ApiResponse.Error(e.code(), e.message())
    } catch (e: IOException) {
        ApiResponse.Exception(e)
    } catch (e: Exception) {
        ApiResponse.Exception(e)
    }
}
