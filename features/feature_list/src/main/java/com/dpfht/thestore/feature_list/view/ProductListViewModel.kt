package com.dpfht.thestore.feature_list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dpfht.thestore.domain.entity.DataDomain
import com.dpfht.thestore.domain.entity.ProductEntity
import com.dpfht.thestore.domain.usecase.GetProductsUseCase
import com.dpfht.thestore.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore.framework.commons.CallbackWrapper
import com.dpfht.thestore.framework.navigation.NavigationService
import com.dpfht.thestore.framework.util.net.OnlineChecker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductListViewModel constructor(
  private val getProdutsUseCase: GetProductsUseCase,
  private val compositeDisposable: CompositeDisposable,
  private val products: ArrayList<ProductEntity>,
  private val onlineChecker: OnlineChecker,
  val adapter: ProductListAdapter,
  private val navigationService: NavigationService
): ViewModel() {

  private val mIsShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = mIsShowDialogLoading

  private val mToastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = mToastMessage

  private val _banner = MutableLiveData<String>()
  val banner: LiveData<String> = _banner

  fun start() {
    if (products.isEmpty()) {
      mToastMessage.postValue(
        if (!onlineChecker.isOnline()) {
          "you are in offline mode"
        } else {
          ""
        }
      )

      getProducts()
    }
  }

  private fun getProducts() {
    mIsShowDialogLoading.postValue(true)

    val subs = getProdutsUseCase()
      .map { it }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<DataDomain>() {
        override fun onSuccessCall(result: DataDomain) {
          onSuccess(
            result.data?.banner ?: "",
            ArrayList( result.data?.products ?: arrayListOf())
          )
        }

        override fun onErrorCall(message: String) {
          onError(message)
        }
      })

    compositeDisposable.add(subs)
  }

  private fun onSuccess(banner: String, products: ArrayList<ProductEntity>) {
    if (banner.isNotEmpty()) {
      _banner.postValue(banner)
    }

    for (product in products) {
      this.products.add(product)
      adapter.notifyItemInserted(this.products.size - 1)
    }

    mIsShowDialogLoading.postValue(false)
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    navigateToErrorMessageView(message)
  }

  fun navigateToProductDetails(position: Int, navController: NavController?) {
    val product = products[position]

    navigationService.navigateToProductDetails(
      product.productName,
      product.price,
      product.description,
      product.images?.large ?: "",
      navController
    )
  }

  private fun navigateToErrorMessageView(message: String) {
    navigationService.navigateToErrorMessageView(message)
  }

  fun refresh() {
    products.clear()
    adapter.notifyDataSetChanged()
    start()
  }

  override fun onCleared() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    super.onCleared()
  }
}
