package com.dpfht.thestore.framework.rest.api

import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.repository.AppDataSource
import io.reactivex.Observable

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override fun getProducts(): Observable<DataResponse> {
    return restService.getProducts()
  }
}
