package com.dpfht.thestore.data.model.remote

import androidx.annotation.Keep

@Keep
data class DataResponse(
  val status: String = "",
  val message: String = "",
  val data: Data? = null
)
