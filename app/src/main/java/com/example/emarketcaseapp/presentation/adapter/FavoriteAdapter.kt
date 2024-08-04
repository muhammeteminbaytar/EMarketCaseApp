package com.example.emarketcaseapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FavoriteItemViewBinding
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.presentation.viewmodel.FavoriteScreenViewModel
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class FavoriteAdapter(
    private val viewModel: FavoriteScreenViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val startClick: (Product) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var items: List<Product> = emptyList()
    fun setItems(items: List<Product>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            FavoriteItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, viewModel, lifecycleOwner, startClick)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}

class FavoriteViewHolder(
    private val binding: FavoriteItemViewBinding,
    private val viewModel: FavoriteScreenViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val startClick: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.favoriteItem = product

        Glide.with(binding.imgFavorite.context)
            .load(product.image)
            .into(binding.imgFavorite)

        lifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteProductIds.collect { favoriteIds ->
                val isFavorite = favoriteIds.contains(product.id)
                binding.imgStar.setImageResource(if (isFavorite) R.drawable.star_fill else R.drawable.star)
            }
        }

        binding.imgStar.setOnClickListener {
            startClick(product)
        }

    }
}
