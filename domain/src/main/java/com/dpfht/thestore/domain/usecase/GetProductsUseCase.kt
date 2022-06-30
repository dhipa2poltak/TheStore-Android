package com.dpfht.thestore.domain.usecase

import com.dpfht.thestore.data.model.remote.DataResponse
import io.reactivex.Observable

interface GetProductsUseCase {

  operator fun invoke(): Observable<DataResponse>
}
