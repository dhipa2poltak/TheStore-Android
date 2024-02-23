package com.dpfht.thestore.framework.data.datasource.remote

import com.dpfht.thestore.data.datasource.AppDataSource
import com.dpfht.thestore.data.model.remote.Data
import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.model.remote.Product
import com.dpfht.thestore.framework.data.datasource.remote.rest.RestService
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
class RemoteDataSourceTest {

  private lateinit var remoteDataSource: AppDataSource

  @Mock
  private lateinit var restService: RestService
  private lateinit var compositeDisposable: CompositeDisposable

  @Before
  fun setup() {
    remoteDataSource = RemoteDataSourceImpl(restService)
    compositeDisposable = CompositeDisposable()
  }

  @After
  fun teardown() {
    compositeDisposable.dispose()
  }

  @Test
  fun `ensure when calling getProducts method in RemoteDataSource, it returns records of product`() {
    val products = arrayListOf(Product(), Product(), Product())
    val data = Data(products = products)
    val dataResponse = DataResponse("200", "success", data)

    whenever(restService.getProducts()).thenReturn(Observable.just(dataResponse))

    var actual: DataResponse? = null

    val disposable = remoteDataSource
      .getProducts()
      .subscribe {
        actual = it
      }

    compositeDisposable.add(disposable)

    assertTrue(actual != null)
    assertTrue(dataResponse == actual)
  }
}
