package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emarketcaseapp.databinding.FragmentCartScreenBinding
import com.example.emarketcaseapp.domain.model.CartModel
import com.example.emarketcaseapp.presentation.adapter.CartAdapter
import com.example.emarketcaseapp.presentation.adapter.OnCartClickListener
import com.example.emarketcaseapp.presentation.adapter.ProductAdapter
import com.example.emarketcaseapp.presentation.viewmodel.CartScreenViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartScreenFragment : Fragment(), OnCartClickListener {
    private lateinit var fragmentCartBinding: FragmentCartScreenBinding
    private val viewModel : CartScreenViewModel by activityViewModels()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCartBinding = FragmentCartScreenBinding.inflate(inflater, container, false)

        adapter = CartAdapter(this)
        fragmentCartBinding.rvCart.adapter = adapter
        fragmentCartBinding.rvCart.layoutManager = LinearLayoutManager(context)

        if (viewModel.products.value.isEmpty()) {
            viewModel.getProducts()
        }

        observeViewModel()

        return fragmentCartBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCartProducts()
    }
    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.filteredProducts.collect{
                adapter.setItems(it)
            }
        }
        lifecycleScope.launch {
            viewModel.totalValue.collect{
                fragmentCartBinding.txtTotalPrice.text = it.toString()
            }
        }
    }

    override fun onMinusClick(cart: CartModel) {
        viewModel.updateCartProduct(cart.product.id, false)
    }

    override fun onPlusClick(cart: CartModel) {
        viewModel.updateCartProduct(cart.product.id, true)
    }
}