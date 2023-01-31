package com.mate.carpool.ui.base

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _snackbarMessage = MutableSharedFlow<SnackBarMessage>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    fun emitEvent(type: String) {
        viewModelScope.launch { _event.emit(Event(type)) }
    }

    fun emitSnackbar(message: SnackBarMessage) {
        viewModelScope.launch { _snackbarMessage.emit(message) } }

    companion object{
        const val EVENT_READY = "EVENT_READY"
    }
}

data class Event(val type: String) {
    companion object {
        fun getInitValues() = Event(
            type = ""
        )
        const val EVENT_READY = "EVENT_READY"
        const val EVENT_FINISH = "EVENT_FINISH"
    }
}

data class SnackBarMessage(
    val headerMessage: String,
    val contentMessage: String = ""
) {
    companion object{
        fun getInitValues() = SnackBarMessage(
            headerMessage = "",
            contentMessage = ""
        )
    }
}