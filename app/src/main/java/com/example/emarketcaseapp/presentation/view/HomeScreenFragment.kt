package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emarketcaseapp.databinding.FragmentHomeScreenBinding
import com.example.emarketcaseapp.presentation.adapter.ProductAdapter
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeScreenBinding
    private val viewModel: HomeScreenViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        adapter = ProductAdapter()
        fragmentHomeBinding.rvHome.adapter = adapter
        fragmentHomeBinding.rvHome.layoutManager = LinearLayoutManager(context)

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
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                fragmentHomeBinding.progressBar.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }

        // Observe error messages
        lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    /*
                          fragmentHomeBinding.tvErrorMessage.text = it
                          fragmentHomeBinding.tvErrorMessage.visibility = View.VISIBLE*/
                } ?: run {
/*
                    fragmentHomeBinding.tvErrorMessage.visibility = View.GONE
*/
                }
            }
        }
    }
}
