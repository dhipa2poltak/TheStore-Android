package com.dpfht.testproductlist.data.repository

import com.dpfht.testproductlist.data.model.remote.DataResponse
import io.reactivex.Observable

interface AppDataSource {

  fun getProducts(): Observable<DataResponse>
}
