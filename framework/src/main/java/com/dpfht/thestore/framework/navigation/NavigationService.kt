package com.dpfht.thestore.framework.navigation

import androidx.navigation.NavController

interface NavigationService {

  fun navigateToProductList()
  fun navigateToProductDetails(title: String, price: Double, desc: String, image: String, navController: NavController?)
  fun navigateToErrorMessageView(message: String)
}
