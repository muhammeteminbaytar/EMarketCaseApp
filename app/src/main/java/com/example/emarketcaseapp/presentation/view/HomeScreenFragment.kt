package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FragmentHomeScreenBinding
import com.example.emarketcaseapp.databinding.FragmentProfileScreenBinding
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel
import com.example.emarketcaseapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeScreenBinding
    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        return fragmentHomeBinding.root
    }

}