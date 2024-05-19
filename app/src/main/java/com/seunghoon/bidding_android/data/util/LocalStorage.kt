package com.seunghoon.bidding_android.data.util

import android.content.Context

class LocalStorage(
    private val context: Context,
) {
    companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    private val preference by lazy {
        context.getSharedPreferences("Bidding", Context.MODE_PRIVATE)
    }

    private val editor by lazy {
        preference.edit()
    }

    internal fun putValue(
        key: String,
        value: String,
    ) {
        editor.putString(key, value).apply()
    }

    internal fun getValue(key: String): String {
        return preference.getString(key, "") ?: ""
    }
}
