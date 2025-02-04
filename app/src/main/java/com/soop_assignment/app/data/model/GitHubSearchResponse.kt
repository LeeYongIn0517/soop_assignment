package com.soop_assignment.app.data.model

import com.google.gson.annotations.SerializedName
data class GitHubSearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<GitHubRepo>
)
