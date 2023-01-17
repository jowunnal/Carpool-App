package com.mate.carpool.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T, M> StateFlow<T>.map(
    coroutineScope : CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(),
    mapper : (value : T) -> M
) : StateFlow<M> = map { mapper(it) }.stateIn(
    coroutineScope,
    started,
    mapper(value)
)