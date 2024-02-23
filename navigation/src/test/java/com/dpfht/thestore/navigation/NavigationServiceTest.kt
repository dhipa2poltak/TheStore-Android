package com.dpfht.thestore.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.thestore.framework.navigation.NavigationService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NavigationServiceTest {

  private lateinit var navigationService: NavigationService
  private lateinit var navController: NavController

  private val title = "title"
  private val price = 10000.0
  private val desc = "description"
  private val image = "image"

  @Before
  fun setup() {
    navController = mock()
    navigationService = NavigationServiceImpl(navController)
  }

  @Test
  fun `ensure navigate method is called in navController when calling navigateToProductDetails method in navigationService`() {
    navigationService.navigateToProductDetails(title, price, desc, image, navController)

    verify(navController).navigate(any<NavDeepLinkRequest>())
  }

  @Test
  fun `ensure navigate method is called in navController when calling navigateToProductDetails method with null value for navController parameter in navigationService`() {
    navigationService.navigateToProductDetails(title, price, desc, image, null)

    verify(navController).navigate(any<NavDeepLinkRequest>())
  }

  @Test
  fun `ensure navigate method is called in navController when calling navigateToErrorMessageView method in navigationService`() {
    val errorMessage = "error message"
    navigationService.navigateToErrorMessageView(errorMessage)
    verify(navController).navigate(any<NavDeepLinkRequest>())
  }
}
