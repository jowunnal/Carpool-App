package com.mate.carpool.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    fun emitEvent(type: String) {
        viewModelScope.launch { _event.emit(Event(type)) }
    }

    fun emitSnackbar(message: String) {
        viewModelScope.launch { _snackbarMessage.emit(message) }
    }
}

data class Event(val type: String)