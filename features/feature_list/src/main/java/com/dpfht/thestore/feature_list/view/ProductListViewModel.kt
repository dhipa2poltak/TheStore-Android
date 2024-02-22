package com.dpfht.thestore.feature_list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dpfht.thestore.domain.entity.DataDomain
import com.dpfht.thestore.domain.entity.ProductEntity
import com.dpfht.thestore.domain.usecase.GetProductsUseCase
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
  private val navigationService: NavigationService
): ViewModel() {

  private val mIsShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = mIsShowDialogLoading

  private val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = mErrorMessage

  private val mToastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = mToastMessage

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int> = _notifyItemInserted

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
      _banner.value = banner
    }

    for (product in products) {
      this.products.add(product)
      _notifyItemInserted.value = this.products.size - 1
    }

    mIsShowDialogLoading.value = false
  }

  private fun onError(message: String) {
    mErrorMessage.value = message
    mIsShowDialogLoading.value = false
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

  fun navigateToErrorMessageView(message: String) {
    navigationService.navigateToErrorMessageView(message)
  }

  fun refresh() {
    products.clear()
    start()
  }

  override fun onCleared() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    super.onCleared()
  }
}
