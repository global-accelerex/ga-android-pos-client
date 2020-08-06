package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries

internal object TerminalInformation {

    var TERMINAL_MODE = ""

    const val NIGERIA_TERMINAL_MODE = "nigeria_terminal_mode"
    const val GHANA_TERMINAL_MODE = "ghana_terminal_mode"

    fun setTerminalMode( country: Countries) {
        if (country == Countries.NIGERIA) {
            TERMINAL_MODE = NIGERIA_TERMINAL_MODE
        } else if (country == Countries.GHANA) {
            TERMINAL_MODE = GHANA_TERMINAL_MODE
        }
    }
}