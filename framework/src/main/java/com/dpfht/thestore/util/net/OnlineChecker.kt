package com.dpfht.thestore.util.net

interface OnlineChecker {

  fun isOnline(): Boolean

  fun setOnNetworkStateChangedListener(listener: OnlineCheckerListener?)

  interface OnlineCheckerListener {
    fun networkStateChanged(isOnline: Boolean)
  }
}
