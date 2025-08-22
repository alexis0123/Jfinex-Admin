package com.jfinex.admin.ui.config.components.xor

import android.util.Base64

fun xorEn(data: String, key: String = "6G&10.lK"): String {
    val keyLength = key.length
    val xorBytes = data.mapIndexed { i, c ->
        (c.code xor key[i % keyLength].code).toByte()
    }.toByteArray()
    return Base64.encodeToString(xorBytes, Base64.NO_WRAP)
}

fun xorDe(encoded: String, key: String = "6G&10.lK"): String {
    val decoded = Base64.decode(encoded, Base64.NO_WRAP)
    val keyLength = key.length
    return decoded.mapIndexed { i, b ->
        (b.toInt() xor key[i % keyLength].code).toChar()
    }.joinToString("")
}