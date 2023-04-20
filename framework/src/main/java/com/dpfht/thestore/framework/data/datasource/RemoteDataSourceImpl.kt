package com.dpfht.thestore.framework.data.datasource

import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.datasource.AppDataSource
import com.dpfht.thestore.framework.data.core.api.rest.RestService
import io.reactivex.Observable

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override fun getProducts(): Observable<DataResponse> {
    return restService.getProducts()
  }
}
