package com.android.aplikasikasir.preview.model

import com.google.gson.annotations.SerializedName

data class ResponseSave(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null
)
