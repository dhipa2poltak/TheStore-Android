package com.dpfht.thestore.feature_list.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.dpfht.thestore.data.model.remote.Data
import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.model.remote.Product
import com.dpfht.thestore.domain.entity.DataDomain
import com.dpfht.thestore.domain.entity.DataEntity
import com.dpfht.thestore.domain.entity.ProductEntity
import com.dpfht.thestore.domain.usecase.GetProductsUseCase
import com.dpfht.thestore.feature_list.RxImmediateSchedulerRule
import com.dpfht.thestore.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore.framework.navigation.NavigationService
import com.dpfht.thestore.framework.data.datasource.local.onlinechecker.OnlineChecker
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
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

  @Mock
  private lateinit var compositeDisposable: CompositeDisposable

  @Mock
  private lateinit var onlineChecker: OnlineChecker

  @Mock
  private lateinit var adapter: ProductListAdapter

  @Mock
  private lateinit var navigationService: NavigationService

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  private val listOfProduct = arrayListOf<ProductEntity>()

  @Before
  fun setup() {
    viewModel = ProductListViewModel(
      getProductsUseCase,
      compositeDisposable,
      listOfProduct,
      onlineChecker,
      adapter,
      navigationService
    )
  }

  @Test
  fun `fetch product successfully`() {
    val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
    val data = DataEntity(banner = "this is a banner", products = products)
    val dataResponse = DataDomain(data)

    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(getProductsUseCase.invoke()).thenReturn(Observable.just(dataResponse))

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    verify(adapter, times(products.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `fetch product in offline state successfully`() {
    val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
    val data = DataEntity(banner = "this is a banner", products = products)
    val dataResponse = DataDomain(data)

    whenever(onlineChecker.isOnline()).thenReturn(false)
    whenever(getProductsUseCase.invoke()).thenReturn(Observable.just(dataResponse))

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    verify(adapter, times(products.size)).notifyItemInserted(anyInt())
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `navigate to Product Details screen`() {
    val theFirstPosition = 0
    val navController: NavController = mock()

    val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
    listOfProduct.addAll(products)

    viewModel.navigateToProductDetails(theFirstPosition, navController)

    val product = products[theFirstPosition]
    verify(navigationService).navigateToProductDetails(
      product.productName,
      product.price,
      product.description,
      product.images?.large ?: "",
      navController
    )
  }

  @Test
  fun `refresh successfully`() {
    val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
    val data = DataEntity(banner = "this is a banner", products = products)
    val dataResponse = DataDomain(data)

    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(getProductsUseCase.invoke()).thenReturn(Observable.just(dataResponse))

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.refresh()

    verify(adapter, times(products.size)).notifyItemInserted(anyInt())
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

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    verify(navigationService).navigateToErrorMessageView(msg)
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
