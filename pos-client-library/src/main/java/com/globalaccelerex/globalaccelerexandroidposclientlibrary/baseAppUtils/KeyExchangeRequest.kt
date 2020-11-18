package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation.GHANA_TERMINAL_MODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation.NIGERIA_TERMINAL_MODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation.TERMINAL_MODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedCallingComponentException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys

internal class KeyExchangeRequest {

    fun performKeyExchange(callingComponent: Any) {
        when (TERMINAL_MODE) {
            NIGERIA_TERMINAL_MODE, GHANA_TERMINAL_MODE -> {
                val intent = Intent(BaseAppConstants.KEY_EXCHANGE_REQUEST_INTENT_ADDRESS)
                when (callingComponent) {
                    is Activity -> {
                        callingComponent.startActivityForResult(intent, GaRequestKeys.KEY_EXCHANGE_REQUEST_CODE)
                    }
                    is Fragment -> {
                        callingComponent.startActivityForResult(intent, GaRequestKeys.KEY_EXCHANGE_REQUEST_CODE)
                    }
                    else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
                }
            }
        }
    }

}