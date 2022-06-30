package com.dpfht.testproductlist.feature_list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.testproductlist.TheApplication
import com.dpfht.testproductlist.feature_list.R
import com.dpfht.testproductlist.feature_list.adapter.ProductListAdapter
import com.dpfht.testproductlist.feature_list.databinding.FragmentProductListBinding
import com.dpfht.testproductlist.feature_list.databinding.FragmentProductListLandBinding
import com.dpfht.testproductlist.feature_list.di.DaggerProductListComponent
import com.dpfht.testproductlist.feature_list.di.ProductListModule
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ProductListFragment : Fragment() {

  @Inject
  lateinit var prgDialog: AlertDialog

  @Inject
  lateinit var viewModel: ProductListViewModel

  @Inject
  lateinit var adapter: ProductListAdapter

  private lateinit var ivBanner: ImageView
  private lateinit var rvProduct: RecyclerView

  private var isTablet = false

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val productListComponent = DaggerProductListComponent
      .builder()
      .productListModule(ProductListModule(this))
      .applicationComponent(TheApplication.instance.applicationComponent)
      .build()

    productListComponent.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    isTablet = requireContext().resources.getBoolean(com.dpfht.testproductlist.framework.R.bool.isTablet)

    val view: View
    if (isTablet) {
      val binding = FragmentProductListLandBinding.inflate(inflater, container, false)

      ivBanner = binding.ivBanner
      rvProduct = binding.rvProduct

      view = binding.root
    } else {
      val binding = FragmentProductListBinding.inflate(inflater, container, false)

      ivBanner = binding.ivBanner
      rvProduct = binding.rvProduct

      view = binding.root
    }

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setToolbar()

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    rvProduct.layoutManager = layoutManager
    rvProduct.adapter = adapter

    adapter.onClickProductListener = object : ProductListAdapter.OnClickProductListener {
      override fun onClickProduct(position: Int) {
        val request = viewModel.getNavDeepLinkRequest(position)

        if (isTablet) {
          val navHostFragment =
            childFragmentManager.findFragmentById(R.id.details_nav_container) as NavHostFragment
          navHostFragment.navController.navigate(request)
        } else {
          Navigation.findNavController(requireView())
            .navigate(request)
        }
      }
    }

    viewModel.isShowDialogLoading.observe(requireActivity()) { isLoading ->
      if (isLoading) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
      }
    }

    viewModel.banner.observe(requireActivity()) { banner ->
      if (banner.isNotEmpty()) {
        Picasso.get().load(banner)
          .error(android.R.drawable.ic_menu_close_clear_cancel)
          //.placeholder(R.drawable.loading)
          .into(ivBanner)
      }
    }

    viewModel.notifyItemInserted.observe(requireActivity()) { position ->
      if (position > 0) {
        adapter.notifyItemInserted(position)
      }
    }

    viewModel.toastMessage.observe(requireActivity()) { msg ->
      if (msg.isNotEmpty()) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.errorMessage.observe(requireActivity()) { msg ->
      if (msg.isNotEmpty()) {
        showErrorMessage(msg)
      }
    }

    viewModel.start()
  }

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.text_order_barang)
  }

  private fun showErrorMessage(message: String) {
    val request = viewModel.getNavDeepLinkRequestErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(request)
  }
}
