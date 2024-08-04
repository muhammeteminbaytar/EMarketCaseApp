package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.emarketcaseapp.databinding.DialogFilterBinding
import com.example.emarketcaseapp.presentation.viewmodel.HomeScreenViewModel

class FilterDialog : DialogFragment() {
    private lateinit var binding: DialogFilterBinding
    private val viewModel: HomeScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterBinding.inflate(inflater, container, false)
        binding.filterViewModel = viewModel
        binding.lifecycleOwner = this
        filterProcess()

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun filterProcess(){
        binding.radioGroup.setOnCheckedChangeListener(viewModel.onCheckedChangeListener)
        viewModel.selectedSortCriteria.asLiveData().observe(viewLifecycleOwner) { checkedId ->
            checkedId?.let {
                binding.radioGroup.check(it)
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.onSortCriteriaChanged(checkedId)
        }
        binding.btnPrimary.setOnClickListener {
            viewModel.applyFilters()
            dismiss()
        }
    }
}