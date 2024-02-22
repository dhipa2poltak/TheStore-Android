package com.dpfht.thestore.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.thestore.framework.ext.toRupiahString
import com.dpfht.thestore.framework.navigation.NavigationService

class NavigationServiceImpl(private val navController: NavController): NavigationService {

  override fun navigateToProductList() {
    val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
    navGraph.setStartDestination(com.dpfht.thestore.framework.R.id.list_nav_graph)

    navController.graph = navGraph
  }

  override fun navigateToProductDetails(title: String, price: Double, desc: String, image: String, navController: NavController?) {
    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("testproduct.dpfht.com")
      .appendPath("details_fragment")
      .appendQueryParameter("title", title)
      .appendQueryParameter("price", "${price.toRupiahString()} / pcs")
      .appendQueryParameter("desc", desc)
      .appendQueryParameter("image", image)

    if (navController != null) {
      navController.navigate(
        NavDeepLinkRequest.Builder
          .fromUri(builder.build())
          .build()
      )
    } else {
      this.navController.navigate(
        NavDeepLinkRequest.Builder
          .fromUri(builder.build())
          .build()
      )
    }
  }

  override fun navigateToErrorMessageView(message: String) {
    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("testproduct.dpfht.com")
      .appendPath("error_list_fragment")
      .appendQueryParameter("message", message)

    navController.navigate(NavDeepLinkRequest.Builder
      .fromUri(builder.build())
      .build())
  }
}
