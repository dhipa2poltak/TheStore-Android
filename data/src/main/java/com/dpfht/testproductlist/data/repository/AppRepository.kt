package com.dpfht.testproductlist.data.repository

import com.dpfht.testproductlist.data.model.remote.DataResponse
import io.reactivex.Observable

interface AppRepository {

  fun getProducts(): Observable<DataResponse>
}
