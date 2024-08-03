package com.example.emarketcaseapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emarketcaseapp.databinding.ProductItemViewBinding
import com.example.emarketcaseapp.domain.model.Product
import javax.inject.Singleton

@Singleton
class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private var items: List<Product> = emptyList()

    fun setItems(items: List<Product>) {
        this.items = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class ProductViewHolder(private val binding: ProductItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.productItem = product
        Glide.with(binding.imageProduct.context)
            .load(product.image)
            .into(binding.imageProduct)
        binding.executePendingBindings()
    }
}