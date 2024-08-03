package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FragmentCartScreenBinding
import com.example.emarketcaseapp.databinding.FragmentFavoriteScreenBinding

class FavoriteScreenFragment : Fragment() {
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteScreenBinding.inflate(inflater, container, false)

        return fragmentFavoriteBinding.root
    }

}