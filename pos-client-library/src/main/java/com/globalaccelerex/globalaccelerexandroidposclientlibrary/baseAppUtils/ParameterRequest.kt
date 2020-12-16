package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.PARAMETERS_REQUEST_FORMAT
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.PARAMETERS_REQUEST_INTENT_ADDRESS
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.REQUEST_DATA_TAG
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation.GHANA_TERMINAL_MODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation.KENYA_TERMINAL_MODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation.NIGERIA_TERMINAL_MODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation.TERMINAL_MODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedCallingComponentException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.PARAMETERS_REQUEST_CODE


/**
 * This class will handle all Terminal Parameter Requests depending on the android terminal and location specified
 * in [GAClientlib] instantiation.
 * */
internal class ParameterRequest {

    fun performParameterRequest(callingComponent: Any) {
        val jsonString = PARAMETERS_REQUEST_FORMAT
        val intent = Intent(PARAMETERS_REQUEST_INTENT_ADDRESS)
        intent.putExtra(REQUEST_DATA_TAG, jsonString)
        when (TERMINAL_MODE) {
            NIGERIA_TERMINAL_MODE, GHANA_TERMINAL_MODE, KENYA_TERMINAL_MODE -> {
                when (callingComponent) {
                    is Activity -> {
                        callingComponent.startActivityForResult(intent, PARAMETERS_REQUEST_CODE)
                    }
                    is Fragment -> {
                        callingComponent.startActivityForResult(intent, PARAMETERS_REQUEST_CODE)
                    }
                    else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
                }
            }
        }
    }

}