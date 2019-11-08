package com.mahmoudalyudeen.plutus.ui.bitcoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mahmoudalyudeen.plutus.databinding.FragmentBitcoinBinding
import org.koin.android.viewmodel.ext.android.viewModel

class BitcoinFragment : Fragment() {

    private val viewModel: BitcoinViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentBitcoinBinding.inflate(inflater, container, false)
        initView(binding)
        return binding.root
    }

    private fun initView(binding: FragmentBitcoinBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}