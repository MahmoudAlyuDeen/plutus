package com.mahmoudalyudeen.plutus.ui.bitcoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mahmoudalyudeen.plutus.databinding.FragmentBitcoinBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BitcoinFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: BitcoinViewModel

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

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