package com.soop_assignment.app.data.model

import com.google.gson.annotations.SerializedName

data class GitHubLicense(
    val key: String,
    val name: String,
    val url: String?,
    @SerializedName("spdx_id") val spdxId: String,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("html_url") val htmlUrl: String?
)
