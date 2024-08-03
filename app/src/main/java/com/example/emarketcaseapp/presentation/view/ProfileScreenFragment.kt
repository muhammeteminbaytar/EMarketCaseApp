package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emarketcaseapp.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment() {
    private lateinit var fragmentProfileBinding: FragmentProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileBinding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return fragmentProfileBinding.root
    }

}