package com.dpfht.testproductlist.framework.rest.api

import com.dpfht.testproductlist.data.model.remote.DataResponse
import com.dpfht.testproductlist.data.repository.AppDataSource
import io.reactivex.Observable

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override fun getProducts(): Observable<DataResponse> {
    return restService.getProducts()
  }
}
