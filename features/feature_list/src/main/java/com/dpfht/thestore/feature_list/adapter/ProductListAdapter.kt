package com.dpfht.thestore.feature_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.thestore.domain.entity.ProductEntity
import com.dpfht.thestore.framework.ext.toRupiahString
import com.dpfht.thestore.feature_list.databinding.RowProductBinding
import com.dpfht.thestore.feature_list.adapter.ProductListAdapter.ProductListViewHolder
import com.squareup.picasso.Picasso

class ProductListAdapter(
  private val products: ArrayList<ProductEntity>
): RecyclerView.Adapter<ProductListViewHolder>() {

  var onClickProductListener: OnClickProductListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
    val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ProductListViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
    val product = products[position]

    holder.bindData(product)
    holder.itemView.setOnClickListener {
      onClickProductListener?.onClickProduct(position)
    }
  }

  override fun getItemCount(): Int {
    return products.size
  }

  class ProductListViewHolder(private val binding: RowProductBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(product: ProductEntity) {
      binding.tvTitle.text = product.productName
      binding.tvPrice.text = product.price.toRupiahString()
      binding.tvStockValue.text = product.stock.toString()

      product.images?.let {
        if (it.thumbnail.isNotEmpty()) {
          Picasso.get().load(it.thumbnail)
            .error(android.R.drawable.ic_menu_close_clear_cancel)
            //.placeholder(R.drawable.loading)
            .into(binding.ivProduct)
        }
      }
    }
  }

  interface OnClickProductListener {
    fun onClickProduct(position: Int)
  }
}
