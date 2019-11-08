package com.mahmoudalyudeen.plutus.ui.bitcoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudalyudeen.plutus.api.CallStatus
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import com.mahmoudalyudeen.plutus.repo.BitcoinRepository
import com.mahmoudalyudeen.plutus.ui.base.BaseApiViewModel

class BitcoinViewModel(private val bitcoinRepository: BitcoinRepository) : BaseApiViewModel() {

    /** List of [BitcoinValue] displayed by the view and private backing property to prevent modification */
    private val _bitcoinValues = bitcoinRepository.bitcoinValues
    val bitcoinValues: LiveData<List<BitcoinValue>>
        get() = _bitcoinValues

    /** Call status displayed by the view and a private backing property to prevent modification */
    private val _callStatus = MutableLiveData<CallStatus>(CallStatus.Idle)
    val callStatus: LiveData<CallStatus>
        get() = _callStatus

    /** Fetching values on viewModel init to avoid unnecessary reloading on configuration changes */
    init {
        fetchBitcoinValues()
    }

    private fun fetchBitcoinValues() {
        _callStatus.value = getBitcoinValuesCallStatus(CallStatus.Loading)
        performApiCall(
            call = { bitcoinRepository.fetchBitcoinValues() },
            onSuccess = { _callStatus.value = getBitcoinValuesCallStatus(CallStatus.Idle) },
            onError = { _callStatus.value = getBitcoinValuesCallStatus(CallStatus.Error) }
        )
    }

    /** Returns [CallStatus.Idle] if [_bitcoinValues] isn't empty and returns [fallbackStatus] otherwise */
    private fun getBitcoinValuesCallStatus(fallbackStatus: CallStatus): CallStatus {
        return if (_bitcoinValues.value.isNullOrEmpty()) fallbackStatus else CallStatus.Idle
    }
}