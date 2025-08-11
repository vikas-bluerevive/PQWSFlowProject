package com.example.pqwsflowproject.model

data class NotificationResponse(
	val data: Data5? = null,
	val success: Boolean? = null,
	val serverTime: Int? = null,
	val message: String? = null
)

data class Sort5(
	val unsorted: Boolean? = null,
	val sorted: Boolean? = null,
	val empty: Boolean? = null
)

data class Data5(
	val number: Int? = null,
	val last: Boolean? = null,
	val size: Int? = null,
	val numberOfElements: Int? = null,
	val totalPages: Int? = null,
	val pageable: Pageable5? = null,
	val sort: Sort? = null,
	val content: List<ContentItem5?>? = null,
	val first: Boolean? = null,
	val totalElements: Int? = null,
	val empty: Boolean? = null
)

data class ContentItem5(
	val sourceDeviceName: String? = null,
	val severity: String? = null,
	val sourceBoxId: String? = null,
	val supplyDeviceName: String? = null,
	val supplyBoxId: String? = null,
	val notificationId: Int? = null,
	val time: String? = null,
	val type: String? = null,
	val message: String? = null,
	val sourceTankName: Any? = null,
	val supplyTankName: Any? = null
)

data class Pageable5(
	val paged: Boolean? = null,
	val pageNumber: Int? = null,
	val offset: Int? = null,
	val pageSize: Int? = null,
	val unpaged: Boolean? = null,
	val sort: Sort5? = null
)

