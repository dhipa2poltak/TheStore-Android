package com.dpfht.testproductlist.framework.rest.api

import com.dpfht.testproductlist.data.model.remote.DataResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestService {

  @GET("b/5ee794370e966a7aa369eafd")
  fun getProducts(): Observable<DataResponse>
}
