package com.dpfht.thestore

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dpfht.thestore.R.id
import com.dpfht.thestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setToolbar()
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(id.my_nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController
    NavigationUI.setupActionBarWithNavController(this, navController)
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
}
