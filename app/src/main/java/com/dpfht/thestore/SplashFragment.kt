package com.dpfht.thestore

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.dpfht.thestore.R.navigation
import com.dpfht.thestore.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding = FragmentSplashBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
                        navigateToNextScreen()
    }, 3000)
  }

  private fun navigateToNextScreen() {
    val navGraph = Navigation.findNavController(requireView()).navInflater.inflate(navigation.nav_graph)
    navGraph.setStartDestination(com.dpfht.thestore.framework.R.id.list_nav_graph)

    Navigation.findNavController(requireView()).graph = navGraph
  }
}
