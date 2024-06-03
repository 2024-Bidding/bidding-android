package com.seunghoon.bidding_android.common

import android.content.Context
import android.icu.text.DecimalFormat
import android.widget.Toast

internal fun Number.formatted(): String {
    return DecimalFormat("#,###").format(this)
}

internal fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
