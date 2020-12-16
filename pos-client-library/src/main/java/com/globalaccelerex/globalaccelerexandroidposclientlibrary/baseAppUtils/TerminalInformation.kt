package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries

internal object TerminalInformation {

    var TERMINAL_MODE = ""

    const val NIGERIA_TERMINAL_MODE = "nigeria_terminal_mode"
    const val GHANA_TERMINAL_MODE = "ghana_terminal_mode"
    const val KENYA_TERMINAL_MODE = "kenya_terminal_mode"

    fun setTerminalMode( country: Countries) {
        TERMINAL_MODE = when (country) {
            Countries.NIGERIA -> {
                NIGERIA_TERMINAL_MODE
            }
            Countries.GHANA -> {
                GHANA_TERMINAL_MODE
            }
            Countries.KENYA -> {
                KENYA_TERMINAL_MODE
            }
        }
    }
}