package com.dpfht.thestore.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Product(
  @SerializedName("product_id")
  val productId: Long = -1,
  @SerializedName("product_name")
  val productName: String = "",
  val price: Double = 0.0,
  val stock: Int = 0,
  val description: String = "",
  val images: Image? = null
)
