package com.dpfht.thestore.framework.rest.api

import com.dpfht.thestore.data.model.remote.DataResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestService {

  //@GET("b/5ee794370e966a7aa369eafd")
  @GET("v1/8e9b0905-00a9-4458-8cc0-5d95154dadb7")
  fun getProducts(): Observable<DataResponse>
}
