package com.dpfht.thestore.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore.domain.entity.DataDomain

@Keep
data class DataResponse(
  val status: String = "",
  val message: String = "",
  val data: Data? = null
)

fun DataResponse.toDomain(): DataDomain {
  return DataDomain(this.data?.toDomain())
}
