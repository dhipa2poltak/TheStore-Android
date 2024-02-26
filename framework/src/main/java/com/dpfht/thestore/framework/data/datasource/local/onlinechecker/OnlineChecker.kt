package com.dpfht.thestore.framework.data.datasource.local.onlinechecker

interface OnlineChecker {

  fun isOnline(): Boolean

  fun setOnNetworkStateChangedListener(listener: OnlineCheckerListener?)

  interface OnlineCheckerListener {
    fun networkStateChanged(isOnline: Boolean)
  }
}
