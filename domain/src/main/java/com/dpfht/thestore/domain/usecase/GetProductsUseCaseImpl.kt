package com.dpfht.thestore.domain.usecase

import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.repository.AppRepository
import io.reactivex.Observable

class GetProductsUseCaseImpl(
  private val appRepository: AppRepository
): GetProductsUseCase {

  override operator fun invoke(): Observable<DataResponse> {
    return appRepository.getProducts()
  }
}
