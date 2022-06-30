package com.dpfht.testproductlist.data.model.remote

import androidx.annotation.Keep

@Keep
data class Data(
  val banner: String = "",
  val products: List<Product> = arrayListOf()
)
