package com.example.yinling.data.model

import com.google.gson.annotations.SerializedName

data class HealthData(
    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("heartRate")
    val heartRate: Int,

    @SerializedName("bloodPressure")
    val bloodPressure: String, // Format: "120/80"

    @SerializedName("temperature")
    val temperature: Double,

    @SerializedName("recordTime")
    val recordTime: String,
)

data class HealthDataResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<HealthData>? = null,
)

data class MedicationReminder(
    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("medicationName")
    val medicationName: String,

    @SerializedName("dosage")
    val dosage: String,

    @SerializedName("frequency")
    val frequency: String, // e.g., "每天2次"

    @SerializedName("nextReminderTime")
    val nextReminderTime: String,

    @SerializedName("lastTakenTime")
    val lastTakenTime: String? = null,

    @SerializedName("status")
    val status: Int, // 0: 未服用, 1: 已服用, 2: 已过期
)

data class MedicationReminderResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<MedicationReminder>? = null,
)

data class HealthAlert(
    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("alertType")
    val alertType: String, // fall_detection, health_anomaly, medication_overdue

    @SerializedName("description")
    val description: String,

    @SerializedName("severity")
    val severity: String, // high, medium, low

    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("isAcknowledged")
    val isAcknowledged: Boolean,
)

data class HealthAlertResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<HealthAlert>? = null,
)
