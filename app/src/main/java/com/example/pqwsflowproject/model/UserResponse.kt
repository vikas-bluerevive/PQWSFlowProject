package com.example.pqwsflowproject.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@SerializedName("data")
	val data: Data2? = null,
	@SerializedName("success")
	val success: Boolean? = null,
	@SerializedName("serverTime")
	val serverTime: Int? = null,
	@SerializedName("message")
	val message: String? = null
)

data class Data2(
	val role: Any? = null,
	val phone: Any? = null,
	val id: Int? = null,
	val isActive: Any? = null,
	val email: String? = null,
	val username: String? = null
)

