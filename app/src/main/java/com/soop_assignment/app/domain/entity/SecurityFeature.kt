package com.soop_assignment.app.domain.entity

import com.google.gson.annotations.SerializedName

data class SecurityFeature(
    @SerializedName("status") val status: String
)
