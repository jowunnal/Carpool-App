package com.mate.carpool.ui.composable.visualtransformation

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {

    private val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when (offset) {
                in 0..3 -> offset
                in 4..7 -> offset + 1
                else -> offset + 2
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when (offset) {
                in 0 until 3 -> offset
                in 3 until 8 -> offset - 1
                else -> offset - 2
            }
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = buildAnnotatedString {
            val result = text.filter { it.isDigit() }.toMutableList()
            if (text.length > 3) { // 0101 -> 010-1
                result.add(3, '-')
            }
            if (text.length > 7) {  // 01012345 -> 010-1234-5
                result.add(8, '-')
            }
            append(result.joinToString(""))
        }
        return TransformedText(text = annotatedString, offsetMapping = offsetMapping)
    }
}