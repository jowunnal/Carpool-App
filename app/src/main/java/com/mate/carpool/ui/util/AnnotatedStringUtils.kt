package com.mate.carpool.ui.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

fun AnnotatedString.Builder.appendBoldText(text: String) {
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append(text)
    }
}