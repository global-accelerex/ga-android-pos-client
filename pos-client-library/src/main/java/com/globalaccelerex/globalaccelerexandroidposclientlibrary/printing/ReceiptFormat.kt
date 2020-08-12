package com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing

import android.content.Context
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.PrintingUtils.ALIGN_CENTER
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.PrintingUtils.getLogoPath
import kotlinx.coroutines.*

class ReceiptFormat(private val context: Context) {

    var printField = arrayListOf<PrintField>()
    var stringField = arrayListOf<StringField>()

    fun addSingleLine(text: String, alignment: Alignment) {
        stringField.add(
            StringField(
                header = TextField(text = text, align = ALIGN_CENTER),
                isMultiline = false
            )
        )
    }

    fun generateReceipt(): Receipt {
        var path: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            path = getLogoPath(context)
        }

        printField.add(
            PrintField(
                path!!,
                stringFields = stringField
            )
        )

        return Receipt(
            printField
        )
    }

    enum class Alignment {
        ALIGN_LEFT, ALIGN_RIGHT, ALIGN_CENTER
    }
}