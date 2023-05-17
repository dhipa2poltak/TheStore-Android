package com.dpfht.thestore.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore.domain.entity.DataEntity
import com.dpfht.thestore.domain.entity.ImageEntity
import com.dpfht.thestore.domain.entity.ProductEntity

@Keep
data class Data(
  val banner: String = "",
  val products: List<Product> = listOf()
)

fun Data.toDomain(): DataEntity {
  val products = this.products
  val productEntities = products.map { ProductEntity(
    it.productId,
    it.productName,
    it.price,
    it.stock,
    it.description,
    ImageEntity(it.images?.thumbnail ?: "", it.images?.large ?: "")
  ) }

  return DataEntity(this.banner, productEntities)
}
