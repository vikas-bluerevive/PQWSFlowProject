package com.example.pqwsflowproject.model
import com.example.pqwsflowproject.Interface.Data
import com.google.gson.annotations.SerializedName

data class LoginResponse2(
	@SerializedName("data")
	val data: Data1? = null,
	@SerializedName("success")
	val success: Boolean? = null,
	@SerializedName("serverTime")
	val serverTime: Int? = null,
	@SerializedName("message")
	val message: String? = null
)

data class Data1(
	val userId: Int? = null
)



