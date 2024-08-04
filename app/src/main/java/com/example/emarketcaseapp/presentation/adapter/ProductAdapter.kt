package com.example.emarketcaseapp.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.ProductItemViewBinding
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class ProductAdapter(
    private val viewModel: HomeScreenViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val listener: OnProductClickListener
) :
    RecyclerView.Adapter<ProductViewHolder>() {

    private var items: List<Product> = emptyList()

    fun setItems(items: List<Product>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, listener, viewModel, lifecycleOwner)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class ProductViewHolder(
    private val binding: ProductItemViewBinding,
    private val listener: OnProductClickListener,
    private val viewModel: HomeScreenViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.productItem = product
        Glide.with(binding.imageProduct.context)
            .load(product.image)
            .into(binding.imageProduct)

        lifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteProductIds.collect { favoriteIds ->
                val isFavorite = favoriteIds.contains(product.id)
                binding.buttonFavorite.setImageResource(if (isFavorite) R.drawable.star_fill else R.drawable.star)
            }
        }

        binding.root.setOnClickListener {
            listener.onProductClick(product)
        }
        binding.buttonFavorite.setOnClickListener {
            listener.onFavoriteClick(product)
        }
        binding.btnAddtocard.setOnClickListener {
            listener.onAddToCartClick(product)
        }

        binding.executePendingBindings()
    }
}