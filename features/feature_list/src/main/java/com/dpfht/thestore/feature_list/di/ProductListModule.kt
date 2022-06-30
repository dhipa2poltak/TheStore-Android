package com.dpfht.thestore.feature_list.di

import android.content.Context
import com.dpfht.thestore.data.model.remote.Product
import com.dpfht.thestore.data.repository.AppRepository
import com.dpfht.thestore.domain.usecase.GetProductsUseCase
import com.dpfht.thestore.domain.usecase.GetProductsUseCaseImpl
import com.dpfht.thestore.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore.feature_list.view.ProductListFragment
import com.dpfht.thestore.feature_list.view.ProductListViewModel
import com.dpfht.thestore.framework.di.ActivityContext
import com.dpfht.thestore.framework.di.FragmentScope
import com.dpfht.thestore.util.net.OnlineChecker
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [FragmentModule::class])
class ProductListModule(private val productListFragment: ProductListFragment) {

  @Provides
  @ActivityContext
  @FragmentScope
  fun getContext(): Context {
    return productListFragment.requireActivity()
  }

  @Provides
  @FragmentScope
  fun provideCompositeDisposable(): CompositeDisposable {
    return CompositeDisposable()
  }

  @Provides
  @FragmentScope
  fun provideProducts(): ArrayList<Product> {
    return arrayListOf()
  }

  @Provides
  @FragmentScope
  fun provideGetProductsUseCase(appRepository: AppRepository): GetProductsUseCase {
    return GetProductsUseCaseImpl(appRepository)
  }

  @Provides
  @FragmentScope
  fun provideProductListViewModel(
    getProductsUseCase: GetProductsUseCase,
    compositeDisposable: CompositeDisposable,
    products: ArrayList<Product>,
    onlineChecker: OnlineChecker
  ): ProductListViewModel {
    return ProductListViewModel(getProductsUseCase, compositeDisposable, products, onlineChecker)
  }

  @Provides
  @FragmentScope
  fun provideProductListAdapter(products: ArrayList<Product>): ProductListAdapter {
    return ProductListAdapter(products)
  }
}
