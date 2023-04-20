package com.dpfht.thestore.data.repository

import com.dpfht.thestore.data.datasource.AppDataSource
import com.dpfht.thestore.data.datasource.NetworkStateDataSource
import com.dpfht.thestore.data.model.remote.toDomain
import com.dpfht.thestore.domain.entity.DataDomain
import com.dpfht.thestore.domain.repository.AppRepository
import io.reactivex.Observable

class AppRepositoryImpl(
  private val remoteDataSource: AppDataSource,
  private val localDataSource: AppDataSource,
  private val onlineChecker: NetworkStateDataSource
): AppRepository {

  override fun getProducts(): Observable<DataDomain> {
    return if (onlineChecker.isOnline())
      remoteDataSource.getProducts()
        .map { it.toDomain() }
    else
      localDataSource.getProducts()
        .map { it.toDomain() }
  }
}
