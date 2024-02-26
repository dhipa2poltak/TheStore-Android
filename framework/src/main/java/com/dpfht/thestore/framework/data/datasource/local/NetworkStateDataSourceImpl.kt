package com.dpfht.thestore.framework.data.datasource.local

import com.dpfht.thestore.data.datasource.NetworkStateDataSource
import com.dpfht.thestore.framework.data.datasource.local.onlinechecker.OnlineChecker

class NetworkStateDataSourceImpl(
  private val onlineChecker: OnlineChecker
): NetworkStateDataSource {

  override fun isOnline() = onlineChecker.isOnline()
}
