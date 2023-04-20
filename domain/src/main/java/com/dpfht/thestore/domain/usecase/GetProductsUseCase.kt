package com.dpfht.thestore.domain.usecase

import com.dpfht.thestore.domain.entity.DataDomain
import io.reactivex.Observable

interface GetProductsUseCase {

  operator fun invoke(): Observable<DataDomain>
}
