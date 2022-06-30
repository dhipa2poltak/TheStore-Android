package com.dpfht.thestore.feature_list.view

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.model.remote.Product
import com.dpfht.thestore.domain.usecase.GetProductsUseCase
import com.dpfht.thestore.ext.toRupiahString
import com.dpfht.thestore.framework.rest.api.CallbackWrapper
import com.dpfht.thestore.util.net.OnlineChecker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductListViewModel constructor(
  private val getProdutsUseCase: GetProductsUseCase,
  private val compositeDisposable: CompositeDisposable,
  private val products: ArrayList<Product>,
  private val onlineChecker: OnlineChecker
): ViewModel() {

  private val mIsShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean>
    get() = mIsShowDialogLoading

  private val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String>
    get() = mErrorMessage

  private val mToastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String>
    get() = mToastMessage

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int>
    get() = _notifyItemInserted

  private val _banner = MutableLiveData<String>()
  val banner: LiveData<String>
    get() = _banner

  fun start() {
    if (products.isEmpty()) {
      if (!onlineChecker.isOnline()) {
        mToastMessage.value = "you are in offline mode"
      }

      getProducts()
    }
  }

  private fun getProducts() {
    mIsShowDialogLoading.value = true

    val subs = getProdutsUseCase()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<DataResponse>() {
        override fun onSuccessCall(result: DataResponse) {
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

  private fun onSuccess(banner: String, products: ArrayList<Product>) {
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

  fun getNavDeepLinkRequest(position: Int): NavDeepLinkRequest {
    val product = products[position]

    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("testproduct.dpfht.com")
      .appendPath("details_fragment")
      .appendQueryParameter("title", product.productName)
      .appendQueryParameter("price", "${product.price.toRupiahString()} / pcs")
      .appendQueryParameter("desc", product.description)
      .appendQueryParameter("image", product.images?.large ?: "")

    return NavDeepLinkRequest.Builder
      .fromUri(builder.build())
      .build()
  }

  fun getNavDeepLinkRequestErrorDialog(msg: String): NavDeepLinkRequest {

    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("testproduct.dpfht.com")
      .appendPath("error_list_fragment")
      .appendQueryParameter("message", msg)

    return NavDeepLinkRequest.Builder
      .fromUri(builder.build())
      .build()
  }

  override fun onCleared() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    super.onCleared()
  }
}
