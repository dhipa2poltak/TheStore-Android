package com.dpfht.thestore.feature_list.view

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
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dpfht.thestore.feature_list.R
import com.dpfht.thestore.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore.feature_list.databinding.FragmentProductListBinding
import com.dpfht.thestore.feature_list.databinding.FragmentProductListLandBinding
import com.dpfht.thestore.feature_list.di.DaggerProductListComponent
import com.dpfht.thestore.feature_list.di.ProductListModule
import com.dpfht.thestore.framework.di.provider.ApplicationComponentProvider
import com.dpfht.thestore.framework.di.provider.NavigationComponentProvider
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
  private lateinit var swRefresh: SwipeRefreshLayout
  private lateinit var rvProduct: RecyclerView

  private var isTablet = false

  private var vw: View? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val productListComponent = DaggerProductListComponent
      .builder()
      .productListModule(ProductListModule(this))
      .navigationComponent((requireActivity() as NavigationComponentProvider).provideNavigationComponent())
      .applicationComponent((requireActivity().applicationContext as ApplicationComponentProvider).provideApplicationComponent())
      .build()

    productListComponent.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    isTablet = requireContext().resources.getBoolean(com.dpfht.thestore.framework.R.bool.isTablet)

    if (isTablet) {
      if (vw == null) {
        val binding = FragmentProductListLandBinding.inflate(inflater, container, false)

        ivBanner = binding.ivBanner
        swRefresh = binding.swRefresh
        rvProduct = binding.rvProduct

        vw = binding.root
      }
    } else {
      if (vw == null) {
        val binding = FragmentProductListBinding.inflate(inflater, container, false)

        ivBanner = binding.ivBanner
        swRefresh = binding.swRefresh
        rvProduct = binding.rvProduct

        vw = binding.root
      }
    }

    return vw
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

        if (isTablet) {
          val navHostFragment =
            childFragmentManager.findFragmentById(R.id.details_nav_container) as NavHostFragment
          viewModel.navigateToProductDetails(position, navHostFragment.navController)
        } else {
          viewModel.navigateToProductDetails(position, null)
        }
      }
    }

    swRefresh.setOnRefreshListener {
      adapter.products.clear()
      adapter.notifyDataSetChanged()
      viewModel.refresh()
    }

    observeViewModel()

    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { isLoading ->
      if (isLoading && !swRefresh.isRefreshing) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
        swRefresh.isRefreshing = false
      }
    }

    viewModel.banner.observe(viewLifecycleOwner) { banner ->
      if (banner.isNotEmpty()) {
        Picasso.get().load(banner)
          .error(android.R.drawable.ic_menu_close_clear_cancel)
          //.placeholder(R.drawable.loading)
          .into(ivBanner)
      }
    }

    viewModel.notifyItemInserted.observe(viewLifecycleOwner) { position ->
      if (position > 0) {
        adapter.notifyItemInserted(position)
      }
    }

    viewModel.toastMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        showErrorMessage(msg)
      }
    }
  }

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.text_order_barang)
    (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }

  private fun showErrorMessage(message: String) {
    viewModel.navigateToErrorMessageView(message)
  }
}
