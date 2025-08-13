package com.example.pqwsflowproject.model

data class SummaryResponse(
	val data: Data12? = null,
	val success: Boolean? = null,
	val errorCode: Any? = null,
	val serverTime: Int? = null,
	val message: String? = null
)

data class Data12(
	val date: String? = null,
	val hours: List<HoursItem?>? = null,
	val totalDayVolumeLitres: Any? = null,
	val deviceId: Int? = null
)

data class HoursItem(
	val bucketEnd: String? = null,
	val waterVolumeLitres: Any? = null,
	val bucketStart: String? = null
)

