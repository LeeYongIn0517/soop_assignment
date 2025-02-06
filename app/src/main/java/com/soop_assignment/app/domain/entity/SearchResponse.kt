package com.soop_assignment.app.domain.entity

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("incomplete_results") val incompleteResults: Boolean? = null,
    @SerializedName("items") val items: List<RepoWithScore>? = null
)
