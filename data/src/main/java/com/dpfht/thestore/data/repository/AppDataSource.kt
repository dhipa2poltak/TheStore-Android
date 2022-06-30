package com.dpfht.thestore.data.repository

import com.dpfht.thestore.data.model.remote.DataResponse
import io.reactivex.Observable

interface AppDataSource {

  fun getProducts(): Observable<DataResponse>
}
