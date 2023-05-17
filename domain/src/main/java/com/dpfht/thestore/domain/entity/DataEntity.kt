package com.dpfht.thestore.domain.entity

data class DataEntity(
  val banner: String = "",
  val products: List<ProductEntity> = listOf()
)
