package com.seunghoon.bidding_android.common

import android.content.Context
import android.icu.text.DecimalFormat
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

internal fun Number.formatted(): String {
    return DecimalFormat("#,###").format(this)
}

internal fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

private fun getFullImageUrl(url: String): String {
    return "https://jobis-store.s3.ap-northeast-2.amazonaws.com/$url"
}

internal fun insertIntoGlide(
    context: Context,
    imageView: ImageView,
    url: String,
) {
    Glide.with(context).load(getFullImageUrl(url)).into(imageView)
}
