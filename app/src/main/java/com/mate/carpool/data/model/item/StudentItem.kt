package com.mate.carpool.data.model.item

import androidx.compose.runtime.Stable

@Stable
data class StudentItem(
    val studentNumber: String,
    val memberName: String,
    val phoneNumber: String
    )