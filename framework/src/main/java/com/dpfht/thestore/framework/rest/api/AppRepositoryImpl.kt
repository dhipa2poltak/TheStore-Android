package com.dpfht.thestore.framework.rest.api

import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.repository.AppDataSource
import com.dpfht.thestore.data.repository.AppRepository
import com.dpfht.thestore.util.net.OnlineChecker
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
