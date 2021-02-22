package com.globalaccelerex.globalaccelerexandroidposclientlibrary.util

internal fun Double.toPosAmount(): String{
    return String.format("%.2f", this)
}


