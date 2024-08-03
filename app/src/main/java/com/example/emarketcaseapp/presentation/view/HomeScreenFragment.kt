package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.FragmentHomeScreenBinding
import com.example.emarketcaseapp.databinding.FragmentProfileScreenBinding

class HomeScreenFragment : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }

}