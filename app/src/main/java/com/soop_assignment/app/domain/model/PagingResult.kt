package com.soop_assignment.app.domain.model

data class PagingResult<T>(
    val data: T,
    val nextPage: Int,
    val lastPage: Int
)
