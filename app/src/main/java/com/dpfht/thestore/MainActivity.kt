package com.dpfht.thestore

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dpfht.thestore.R.id
import com.dpfht.thestore.databinding.ActivityMainBinding
import com.dpfht.thestore.framework.BroadcastConstants

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

    registerReceiver(enterHomeReceiver, IntentFilter(BroadcastConstants.BROADCAST_ENTER_HOME))
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

  private val enterHomeReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      unregisterReceiver(this)

      val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
      navGraph.setStartDestination(com.dpfht.thestore.framework.R.id.list_nav_graph)

      navController.graph = navGraph
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    try {
      unregisterReceiver(enterHomeReceiver)
    } catch (e: Exception) {
      //--ignore
    }
  }
}
