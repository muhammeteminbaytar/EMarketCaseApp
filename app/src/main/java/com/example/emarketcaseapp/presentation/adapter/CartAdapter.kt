package com.example.emarketcaseapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emarketcaseapp.databinding.CartItemViewBinding
import com.example.emarketcaseapp.databinding.ProductItemViewBinding
import com.example.emarketcaseapp.domain.model.CartModel
import com.example.emarketcaseapp.domain.model.Product
import javax.inject.Singleton

@Singleton
class CartAdapter(

) :  RecyclerView.Adapter<CartViewHolder>(){

    private var items: List<CartModel> = emptyList()
    fun setItems(items: List<CartModel>) {
        this.items = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            CartItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}

class CartViewHolder(
    private val binding: CartItemViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartModel: CartModel){
        binding.cartItem = cartModel
    }
}