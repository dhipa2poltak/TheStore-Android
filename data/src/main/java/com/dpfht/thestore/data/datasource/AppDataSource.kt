package com.dpfht.thestore.data.datasource

import com.dpfht.thestore.data.model.remote.DataResponse
import io.reactivex.Observable

interface AppDataSource {

  fun getProducts(): Observable<DataResponse>
}
