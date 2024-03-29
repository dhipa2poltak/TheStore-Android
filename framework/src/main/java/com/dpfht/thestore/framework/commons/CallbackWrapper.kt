package com.dpfht.thestore.framework.commons

import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException

abstract class CallbackWrapper<T : Any>: DisposableObserver<T>() {

  override fun onNext(t: T) {
    onSuccessCall(t)
  }

  override fun onError(e: Throwable) {
    when (e) {
      is HttpException -> {
        //val response = e.response()

        //val errorResponse = response?.let { ErrorUtil.parseApiError(it) }
        //onErrorCall(errorResponse?.statusMessage ?: "")

        onErrorCall(e.message ?: "error in response")
      }
      is IOException -> {
        onErrorCall(e.message ?: "error in connection")
      } else -> {
      onErrorCall(e.message ?: "error in conversion")
    }
    }
  }

  override fun onComplete() {}

  protected abstract fun onSuccessCall(result: T)

  protected abstract fun onErrorCall(message: String)

  //protected abstract fun onCancelCall()
}
