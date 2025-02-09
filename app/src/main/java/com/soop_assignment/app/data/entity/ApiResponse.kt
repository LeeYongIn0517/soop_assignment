package com.soop_assignment.app.data.entity

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
                return ApiResponse.Success(body, response.headers()["Link"])
            } else {
                return ApiResponse.Error(response.code(), "Response body is null")
            }
        }

        ApiResponse.Error(response.code(), ErrorType.fromCode(response.code()))

    } catch (e: HttpException) {
        ApiResponse.Error(e.code(), e.message())
    } catch (e: IOException) {
        ApiResponse.Exception(e)
    } catch (e: Exception) {
        ApiResponse.Exception(e)
    }
}
