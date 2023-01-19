package com.mate.carpool.data.model.domain.item

import androidx.databinding.ObservableField

data class RegisterItem(
    val kind:ObservableField<String>,
    var input:ObservableField<String?>,
    var helperText:ObservableField<String?>
    )