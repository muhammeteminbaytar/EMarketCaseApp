package com.example.emarketcaseapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emarketcaseapp.R
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

    }

}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
}