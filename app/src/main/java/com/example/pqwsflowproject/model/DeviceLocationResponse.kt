package com.example.pqwsflowproject.model

data class DeviceLocationResponse(
	val data: Data7? = null,
	val success: Boolean? = null,
	val serverTime: Int? = null,
	val message: String? = null
)

data class Data7(
	val latitude: Double? = null,
	val tankId: Int? = null,
	val deviceId: String? = null,
	val status: String? = null,
	val longitude: Double? = null
)

