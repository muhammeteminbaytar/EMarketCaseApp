package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FragmentCartScreenBinding
import com.example.emarketcaseapp.databinding.FragmentFavoriteScreenBinding
import com.example.emarketcaseapp.presentation.adapter.FavoriteAdapter
import com.example.emarketcaseapp.presentation.adapter.ProductAdapter
import com.example.emarketcaseapp.presentation.viewmodel.FavoriteScreenViewModel
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch

class FavoriteScreenFragment : Fragment() {
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteScreenBinding
    val viewModel: FavoriteScreenViewModel by activityViewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteScreenBinding.inflate(inflater, container, false)

        adapter = FavoriteAdapter(viewModel, viewLifecycleOwner) {
            viewModel.toggleFavorite(it.id)
        }
        fragmentFavoriteBinding.rvFavotite.adapter = adapter
        fragmentFavoriteBinding.rvFavotite.layoutManager = LinearLayoutManager(context)

        observeViewModel()


        return fragmentFavoriteBinding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.favoriteProducts.collect { products ->
                adapter.setItems(products)
            }
        }
    }

}