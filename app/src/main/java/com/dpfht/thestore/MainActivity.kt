package com.dpfht.thestore

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.dpfht.thestore.framework.R as FrameworkR
import com.dpfht.thestore.databinding.ActivityMainBinding
import com.dpfht.thestore.framework.di.NavigationComponent
import com.dpfht.thestore.framework.di.DaggerNavigationComponent
import com.dpfht.thestore.framework.di.module.NavigationModule
import com.dpfht.thestore.framework.di.provider.NavigationComponentProvider
import com.dpfht.thestore.navigation.NavigationServiceImpl

class MainActivity : AppCompatActivity(), NavigationComponentProvider {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  private lateinit var navigationComponent: NavigationComponent

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setToolbar()
    setContentView(binding.root)

    val appBarConfiguration = AppBarConfiguration(
      setOf(FrameworkR.id.productListFragment)
    )

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController

    navigationComponent = DaggerNavigationComponent
      .builder()
      .navigationModule(NavigationModule(NavigationServiceImpl(navController)))
      .build()

    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  private fun setToolbar() {
    setSupportActionBar(binding.toolbar)

    when (baseContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
      Configuration.UI_MODE_NIGHT_YES -> {
        binding.toolbar.setTitleTextColor(0xFFFFFFFF.toInt())
      }
      Configuration.UI_MODE_NIGHT_NO -> {
        binding.toolbar.setTitleTextColor(0xFFFF0000.toInt())
      }
      Configuration.UI_MODE_NIGHT_UNDEFINED -> {
        binding.toolbar.setTitleTextColor(0xFFFFFFFF.toInt())
      }
    }
  }

  override fun provideNavigationComponent(): NavigationComponent {
    return navigationComponent
  }
}
