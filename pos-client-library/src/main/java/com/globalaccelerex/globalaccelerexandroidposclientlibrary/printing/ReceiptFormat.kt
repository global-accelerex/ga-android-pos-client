package com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing

import android.content.Context
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.PrintingUtils.getLogoPath
import kotlinx.coroutines.*

class ReceiptFormat(private val context: Context) {

    private val printFields = arrayListOf<PrintField>()
    private val stringField = arrayListOf<StringField>()

    private val merchantFooter = "***** MERCHANT COPY *****"

    private val customerFooter = "***** CUSTOMER COPY *****"

    fun addLineDivider(){
        addSingleLine(text =  "-".repeat(32), textAlignment = TextAlignment.ALIGN_CENTER)
    }

    fun addSpaceDivider(){
        addSingleLine(text = " ".repeat(25), textAlignment = TextAlignment.ALIGN_CENTER)
    }

    fun addSingleLine(text: String, textAlignment: TextAlignment = TextAlignment.ALIGN_LEFT, fontSize: FontSize? = null, isBold: Boolean = false) {
        stringField.add(
            StringField(
                header = TextField(text = text, align = textAlignment.getValue(), size = fontSize?.getValue(), isBold = isBold),
                isMultiline = false
            )
        )
    }
    
    fun addKeyValuePair(key: String, value: String, fontSize: FontSize? = null, isBold: Boolean = false, isMultiLine: Boolean = false) {
        stringField.add(
            StringField(
                header = TextField(text = key, align = TextAlignment.ALIGN_LEFT.getValue(), size = fontSize?.getValue(), isBold = isBold),
                body = TextField(text = value, align = TextAlignment.ALIGN_LEFT.getValue(), size = fontSize?.getValue(), isBold = isBold),
                isMultiline = isMultiLine
            )
        )
    }

    fun generateReceipt(): Receipt {

        var path: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            path = getLogoPath(context)
        }

        printFields.add(
            PrintField(
                path!!,
                stringFields = stringField
            )
        )

        return Receipt(
            printFields
        )
    }

    fun generatePaymentReceipt(): Receipt {

        var path: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            path = getLogoPath(context)
        }

        val customerStringFields = arrayListOf<StringField>()
        customerStringFields.addAll(stringField)
        customerStringFields.add(StringField(header = TextField(customerFooter, align = TextAlignment.ALIGN_CENTER.getValue()), isMultiline = false))
        val merchantStringFields = arrayListOf<StringField>()
        merchantStringFields.addAll(stringField)
        merchantStringFields.add(StringField(header = TextField(merchantFooter, align = TextAlignment.ALIGN_CENTER.getValue()), isMultiline = false))
        printFields.clear()
        printFields.add(
            PrintField(
                path?:"",
                stringFields = customerStringFields
            )
        )
        printFields.add(
            PrintField(
                path?:"",
                stringFields = merchantStringFields
            )
        )
        return Receipt(
            printFields
        )
    }
}

enum class TextAlignment {
    ALIGN_LEFT, ALIGN_RIGHT, ALIGN_CENTER
}

internal fun TextAlignment.getValue(): String{
    return when (this) {
        TextAlignment.ALIGN_LEFT -> "left"
        TextAlignment.ALIGN_CENTER -> "center"
        TextAlignment.ALIGN_RIGHT -> "right"
    }
}

enum class FontSize {
    SMALL, LARGE
}

internal fun FontSize.getValue(): String {
    return when(this){
        FontSize.SMALL -> "small"
        FontSize.LARGE -> "large"
    }
}