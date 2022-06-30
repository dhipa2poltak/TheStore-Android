package com.dpfht.testproductlist.domain.usecase

import com.dpfht.testproductlist.data.model.remote.DataResponse
import com.dpfht.testproductlist.data.repository.AppRepository
import io.reactivex.Observable

class GetProductsUseCaseImpl(
  private val appRepository: AppRepository
): GetProductsUseCase {

  override operator fun invoke(): Observable<DataResponse> {
    return appRepository.getProducts()
  }
}
