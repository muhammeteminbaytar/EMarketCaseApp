package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.emarketcaseapp.databinding.FragmentHomeScreenBinding
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.presentation.adapter.OnProductClickListener
import com.example.emarketcaseapp.presentation.adapter.ProductAdapter
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import com.example.emarketcaseapp.util.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeScreenFragment : Fragment(), OnProductClickListener {

    private lateinit var fragmentHomeBinding: FragmentHomeScreenBinding
    private val viewModel: HomeScreenViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        adapter = ProductAdapter(this)
        fragmentHomeBinding.rvHome.adapter = adapter
        fragmentHomeBinding.rvHome.layoutManager = GridLayoutManager(context, 2)

        val spacingInPixels = 16
        val includeEdge = true
        fragmentHomeBinding.rvHome.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                spacingInPixels,
                includeEdge
            )
        )


        observeViewModel()

        if (viewModel.products.value.isEmpty()) {
            viewModel.getProducts()
        }

        return fragmentHomeBinding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.products.collect { products ->
                adapter.setItems(products)
                fragmentHomeBinding.txtErrorMessage.visibility = View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                fragmentHomeBinding.progressBar.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    fragmentHomeBinding.txtErrorMessage.text = it
                    fragmentHomeBinding.txtErrorMessage.visibility = View.VISIBLE

                } ?: run {
                    fragmentHomeBinding.txtErrorMessage.visibility = View.GONE

                }
            }
        }
    }

    override fun onProductClick(product: Product) {
        println()
    }

    override fun onFavoriteClick(product: Product) {
        println()
    }

    override fun onAddToCartClick(product: Product) {
        println()
    }
}
