package com.mate.carpool.util

import android.telephony.PhoneNumberUtils
import java.util.Locale

fun String.formatPhoneNumber(): String = try {
    PhoneNumberUtils.formatNumber(this, Locale.KOREA.country)
} catch (e: NullPointerException) {
    this
}
