package com.dpfht.thestore.data.model.remote

import androidx.annotation.Keep

@Keep
data class Image(
  val thumbnail: String = "",
  val large: String = ""
)
