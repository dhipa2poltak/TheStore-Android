package com.dpfht.thestore.domain.usecase

import com.dpfht.thestore.domain.entity.DataDomain
import com.dpfht.thestore.domain.repository.AppRepository
import io.reactivex.Observable

class GetProductsUseCaseImpl(
  private val appRepository: AppRepository
): GetProductsUseCase {

  override operator fun invoke(): Observable<DataDomain> {
    return appRepository.getProducts()
  }
}
