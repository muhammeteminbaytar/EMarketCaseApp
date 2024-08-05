package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FragmentDetailScreenBinding
import com.example.emarketcaseapp.databinding.FragmentHomeScreenBinding
import com.example.emarketcaseapp.domain.model.ProductDetail
import com.example.emarketcaseapp.presentation.viewmodel.DetailScreenViewModel
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch

class DetailScreenFragment : Fragment() {
    private lateinit var fragmentDetailBinding: FragmentDetailScreenBinding
    val viewModel: DetailScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDetailBinding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        val args = DetailScreenFragmentArgs.fromBundle(requireArguments())

        val productDetail = args.productDetail

        fragmentDetailBinding.detailFragment = productDetail

        Glide.with(fragmentDetailBinding.detailImage.context)
            .load(productDetail.image)
            .into(fragmentDetailBinding.detailImage)


        fragmentDetailBinding.imgFavorite.setOnClickListener {
            viewModel.toggleFavorite(productDetail.id)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFavorite(productDetail.id).collect {
                fragmentDetailBinding.imgFavorite.setImageResource(if (it) R.drawable.star_fill else R.drawable.star)
            }
        }



        return fragmentDetailBinding.root
    }

}