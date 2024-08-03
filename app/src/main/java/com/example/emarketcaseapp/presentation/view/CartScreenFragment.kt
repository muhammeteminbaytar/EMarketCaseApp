package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emarketcaseapp.databinding.FragmentCartScreenBinding

class CartScreenFragment : Fragment() {
    private lateinit var fragmentCartBinding: FragmentCartScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCartBinding = FragmentCartScreenBinding.inflate(inflater, container, false)

        return fragmentCartBinding.root
    }
}