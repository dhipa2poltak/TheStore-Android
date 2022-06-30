package com.dpfht.testproductlist.framework.rest.api

import com.dpfht.testproductlist.data.model.remote.DataResponse
import com.dpfht.testproductlist.data.repository.AppDataSource
import com.dpfht.testproductlist.data.repository.AppRepository
import com.dpfht.testproductlist.util.net.OnlineChecker
import io.reactivex.Observable

class AppRepositoryImpl(
  private val remoteDataSource: AppDataSource,
  private val localDataSource: AppDataSource,
  private val onlineChecker: OnlineChecker
): AppRepository {

  override fun getProducts(): Observable<DataResponse> {
    return if (onlineChecker.isOnline())
      remoteDataSource.getProducts()
    else
      localDataSource.getProducts()
  }
}
