package com.dpfht.testproductlist.domain.usecase

import com.dpfht.testproductlist.data.model.remote.DataResponse
import io.reactivex.Observable

interface GetProductsUseCase {

  operator fun invoke(): Observable<DataResponse>
}
