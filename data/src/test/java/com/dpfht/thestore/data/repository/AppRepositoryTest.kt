package com.dpfht.thestore.data.repository

import com.dpfht.thestore.data.datasource.AppDataSource
import com.dpfht.thestore.data.datasource.NetworkStateDataSource
import com.dpfht.thestore.data.model.remote.Data
import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.model.remote.Product
import com.dpfht.thestore.domain.entity.DataDomain
import com.dpfht.thestore.domain.repository.AppRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AppRepositoryTest {

  private lateinit var appRepository: AppRepository

  @Mock
  private lateinit var remoteDataSource: AppDataSource

  @Mock
  private lateinit var localDataSource: AppDataSource

  @Mock
  private lateinit var onlineChecker: NetworkStateDataSource

  private lateinit var compositeDisposable: CompositeDisposable

  private val products = arrayListOf(Product(), Product(), Product())
  private val data = Data(products = products)
  private val dataResponse = DataResponse("200", "success", data)

  @Before
  fun setup() {
    appRepository = AppRepositoryImpl(remoteDataSource, localDataSource, onlineChecker)
    compositeDisposable = CompositeDisposable()
  }

  @After
  fun teardown() {
    compositeDisposable.dispose()
  }

  @Test
  fun `fetch product in online condition successfully`() {
    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(remoteDataSource.getProducts()).thenReturn(Observable.just(dataResponse))

    val expected = products.size
    var dataDomain: DataDomain? = null

    val disposable = appRepository
      .getProducts()
      .subscribe {
        dataDomain = it
      }

    compositeDisposable.add(disposable)

    assertTrue(dataDomain != null)
    assertTrue(expected == (dataDomain?.data?.products?.size))
  }

  @Test
  fun `fetch product in offline condition successfully`() {
    whenever(onlineChecker.isOnline()).thenReturn(false)
    whenever(localDataSource.getProducts()).thenReturn(Observable.just(dataResponse))

    val expected = products.size
    var dataDomain: DataDomain? = null

    val disposable = appRepository
      .getProducts()
      .subscribe {
        dataDomain = it
      }

    compositeDisposable.add(disposable)

    assertTrue(dataDomain != null)
    assertTrue(expected == (dataDomain?.data?.products?.size))
  }
}
