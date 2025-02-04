package com.soop_assignment.app.data.model

import com.google.gson.annotations.SerializedName

data class SecurityAndAnalysis(
    @SerializedName("secret_scanning") val secretScanning: SecurityFeature,
    @SerializedName("secret_scanning_push_protection") val secretScanningPushProtection: SecurityFeature,
    @SerializedName("dependabot_security_updates") val dependabotSecurityUpdates: SecurityFeature,
    @SerializedName("secret_scanning_non_provider_patterns") val secretScanningNonProviderPatterns: SecurityFeature,
    @SerializedName("secret_scanning_validity_checks") val secretScanningValidityChecks: SecurityFeature
)
