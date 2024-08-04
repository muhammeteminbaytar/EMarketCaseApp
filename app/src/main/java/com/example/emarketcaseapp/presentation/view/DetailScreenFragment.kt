package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FragmentDetailScreenBinding
import com.example.emarketcaseapp.databinding.FragmentHomeScreenBinding
import com.example.emarketcaseapp.domain.model.ProductDetail

class DetailScreenFragment : Fragment() {
    private lateinit var fragmentDetailBinding: FragmentDetailScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDetailBinding = FragmentDetailScreenBinding.inflate(inflater, container, false)

        val args = DetailScreenFragmentArgs.fromBundle(requireArguments())
        val productDetail = args.productDetail as ProductDetail

        return fragmentDetailBinding.root
    }
}