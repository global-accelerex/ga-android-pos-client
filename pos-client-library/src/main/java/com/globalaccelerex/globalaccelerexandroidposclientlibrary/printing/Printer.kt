package com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.PRINTER_INTENT_ADDRESS
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedCallingComponentException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.PRINTING_REQUEST_CODE
import com.google.gson.Gson

class Printer {

    fun printReceipt(receipt: Receipt, callingComponent: Any) {
        val intent = Intent(PRINTER_INTENT_ADDRESS)
        intent.putExtra("jsonData", Gson().toJson(receipt, Receipt::class.java))
        when (callingComponent) {
            is Activity -> {
                callingComponent.startActivityForResult(intent, PRINTING_REQUEST_CODE)
            }
            is Fragment -> {
                callingComponent.startActivityForResult(intent, PRINTING_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Make sure you are calling from a supported android component.")
        }
    }

}