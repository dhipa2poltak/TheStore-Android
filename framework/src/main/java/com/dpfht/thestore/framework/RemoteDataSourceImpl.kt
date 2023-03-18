package com.dpfht.thestore.framework

import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.repository.AppDataSource
import com.dpfht.thestore.framework.rest.api.RestService
import io.reactivex.Observable

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override fun getProducts(): Observable<DataResponse> {
    return restService.getProducts()
  }
}
