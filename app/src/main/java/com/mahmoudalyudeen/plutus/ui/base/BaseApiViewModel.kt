package com.mahmoudalyudeen.plutus.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

/** Base ViewModel to perform API calls and manage loading and error states */
open class BaseApiViewModel : ViewModel() {

    /** Job and Scope to launch/cancel coroutines */
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /** Performs a suspended [call] with [viewModelScope] and calls [onSuccess] afterwards */
    protected fun performApiCall(call: suspend () -> Unit, onSuccess: () -> Unit = {}, onError: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                call()
                onSuccess()
            } catch (exception: Exception) {
                onError()
            }
        }
    }

    /** Cancel any pending coroutines */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}