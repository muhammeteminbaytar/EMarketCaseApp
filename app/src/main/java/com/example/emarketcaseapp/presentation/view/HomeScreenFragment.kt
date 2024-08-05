package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FragmentHomeScreenBinding
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.domain.model.ProductDetail
import com.example.emarketcaseapp.presentation.adapter.OnProductClickListener
import com.example.emarketcaseapp.presentation.adapter.ProductAdapter
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import com.example.emarketcaseapp.util.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeScreenFragment : Fragment(), OnProductClickListener {

    private lateinit var fragmentHomeBinding: FragmentHomeScreenBinding
    val viewModel: HomeScreenViewModel by activityViewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        adapter = ProductAdapter(viewModel, viewLifecycleOwner, this)
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
            viewModel.searchResults.collect { products ->
                adapter.setItems(products)
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
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                } ?: run {

                }
            }
        }
    }

    override fun onProductClick(product: Product) {
        var mIsFavorite = false
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteProductIds.collect { favoriteIds ->
                mIsFavorite = favoriteIds.contains(product.id)
            }
        }

        val productDetail = ProductDetail(
            brand = product.brand,
            createdAt = product.createdAt,
            description = product.description,
            id = product.id,
            image = product.image,
            model = product.model,
            name = product.name,
            price = product.price,
            isFavorite = mIsFavorite
        )
        val action = HomeScreenFragmentDirections.homeToDetail(productDetail)
        findNavController().navigate(action)
    }

    override fun onFavoriteClick(product: Product) {
        viewModel.toggleFavorite(product.id)
    }

    override fun onAddToCartClick(product: Product) {
        viewModel.updateCartProduct(product.id, true)
        Toast.makeText(context, product.name + " " + getString(R.string.added), Toast.LENGTH_SHORT).show()
    }
}
