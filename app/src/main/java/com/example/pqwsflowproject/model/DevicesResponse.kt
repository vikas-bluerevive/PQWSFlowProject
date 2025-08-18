package com.example.pqwsflowproject.model

data class DevicesResponse(
	val data: Data8? = null,
	val success: Boolean? = null,
	val serverTime: Int? = null,
	val message: String? = null
)

data class Data8(
	val number: Int? = null,
	val last: Boolean? = null,
	val size: Int? = null,
	val numberOfElements: Int? = null,
	val totalPages: Int? = null,
	val pageable: Pageable8? = null,
	val sort: Sort8? = null,
	val content: List<ContentItem8?>? = null,
	val first: Boolean? = null,
	val totalElements: Int? = null,
	val empty: Boolean? = null
)

data class Pageable8(
	val paged: Boolean? = null,
	val pageNumber: Int? = null,
	val offset: Int? = null,
	val pageSize: Int? = null,
	val unpaged: Boolean? = null,
	val sort: Sort? = null
)

data class Sort8(
	val unsorted: Boolean? = null,
	val sorted: Boolean? = null,
	val empty: Boolean? = null
)

data class ContentItem8(
	val tankName: String? = null,
	val tankId: Int? = null,
	val deviceId: String? = null
)

