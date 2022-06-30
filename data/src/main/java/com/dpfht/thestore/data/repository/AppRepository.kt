package com.dpfht.thestore.data.repository

import com.dpfht.thestore.data.model.remote.DataResponse
import io.reactivex.Observable

interface AppRepository {

  fun getProducts(): Observable<DataResponse>
}
