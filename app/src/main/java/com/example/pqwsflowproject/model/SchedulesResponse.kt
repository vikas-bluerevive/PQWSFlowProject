package com.example.pqwsflowproject.model

data class SchedulesResponse(
	val data: Data11? = null,
	val success: Boolean? = null,
	val serverTime: Int? = null,
	val message: String? = null
)

data class UpcomingItem(
	val targetTankLocation: String? = null,
	val active: Boolean? = null,
	val id: Int? = null,
	val time: String? = null,
	val targetTankName: String? = null,
	val day: Any? = null
)

data class Data11(
	val past: List<PastItem?>? = null,
	val upcoming: List<UpcomingItem?>? = null
)

data class PastItem(
	val targetTankLocation: String? = null,
	val active: Boolean? = null,
	val id: Int? = null,
	val time: String? = null,
	val targetTankName: String? = null,
	val day: Any? = null
)

