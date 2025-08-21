package com.example.pqwsflowproject.model

data class WaterSummaryResponse(
	val data: Data9? = null,
	val success: Boolean? = null,
	val serverTime: Int? = null,
	val message: String? = null
)

data class HoursItem2(
	val bucketEnd: String? = null,
	val waterVolumeLitres: Any? = null,
	val bucketStart: String? = null
)

data class Data9(
	val date: String? = null,
	val hours: List<HoursItem2?>? = null,
	val totalDayVolumeLitres: Any? = null
)

