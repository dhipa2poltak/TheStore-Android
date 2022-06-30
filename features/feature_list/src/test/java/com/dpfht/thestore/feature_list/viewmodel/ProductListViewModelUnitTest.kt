package com.dpfht.thestore.feature_list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.thestore.data.model.remote.Data
import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.model.remote.Product
import com.dpfht.thestore.domain.usecase.GetProductsUseCase
import com.dpfht.thestore.feature_list.RxImmediateSchedulerRule
import com.dpfht.thestore.feature_list.view.ProductListViewModel
import com.dpfht.thestore.util.net.OnlineChecker
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ProductListViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var viewModel: ProductListViewModel

  @Mock
  private lateinit var getProductsUseCase: GetProductsUseCase

  private val listOfProduct = arrayListOf<Product>()
  private val compositeDisposable = CompositeDisposable()

  @Mock
  private lateinit var onlineChecker: OnlineChecker

  @Mock
  private lateinit var notifyItemInsertedObserver: Observer<Int>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Before
  fun setup() {
    viewModel = ProductListViewModel(getProductsUseCase, compositeDisposable, listOfProduct, onlineChecker)
  }

  @Test
  fun `fetch product successfully`() {
    val products = arrayListOf(Product(), Product(), Product())
    val data = Data(products = products)
    val dataResponse = DataResponse("200", "success", data)

    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(getProductsUseCase.invoke()).thenReturn(Observable.just(dataResponse))

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.errorMessage.observeForever(errorMessageObserver)

    viewModel.start()

    verify(notifyItemInsertedObserver).onChanged(eq(listOfProduct.size - 1))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch product`() {
    val msg = "error in conversion"

    val products = arrayListOf(Product(), Product(), Product())
    val data = Data(products = products)
    val dataResponse = DataResponse("200", "success", data)

    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(getProductsUseCase.invoke()).thenReturn(
      Observable.just(dataResponse)
        .map { throw Exception(msg) }
    )

    viewModel.notifyItemInserted.observeForever(notifyItemInsertedObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.errorMessage.observeForever(errorMessageObserver)

    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
