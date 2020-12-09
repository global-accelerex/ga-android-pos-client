package com.globalaccelerex.androidposclientlibrary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.GAClientLib
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.PosParameters
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.FontSize
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.ReceiptFormat
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.TextAlignment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This activity shows an example of how each library function is used.
 * Same principles apply when calling from a fragment
 * */

class ActivitySample : AppCompatActivity() {

    private val clientLib = GAClientLib.Builder()
        .setCountryCode(Countries.KENYA)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make Key Exchange
        key_exchange.setOnClickListener {
            clientLib.makeKeyExchangeRequest(this)
            Log.e("key exchange", "Called!")
        }

        //Make parameter request
        paramter_request.setOnClickListener {
            clientLib.makeParametersRequest(this)
        }

        //Make Card Present Transactions (CP)
        transaction_request.setOnClickListener {
            clientLib.cardTransactions.purchase(
                amount = 0.01,
                callingComponent = this,
                customPrint = true
            )
        }
        mobile_money_request.setOnClickListener {
            clientLib.mobileMoneyTransaction.purchase(
                phoneNumber = "8133251805",
                amount = 1.0,
                mobileOperator = MobileMoneyOperators.MTN,
                callingComponent = this
            )
        }

        print_receipt.setOnClickListener {
            val receiptFormat = ReceiptFormat(applicationContext)
            receiptFormat.addSingleLine("This is a default line")
            receiptFormat.addSingleLine("This is a bold line", isBold = true)
            receiptFormat.addSingleLine("This is a large text", isBold = true, fontSize = FontSize.LARGE)
            receiptFormat.addSingleLine("This is the first printing line also testing multiline so this text has to be very very long", TextAlignment.ALIGN_LEFT)
            receiptFormat.addSpaceDivider()
            receiptFormat.addSingleLine("Just to confirm!", TextAlignment.ALIGN_RIGHT)
            receiptFormat.addLineDivider()
            receiptFormat.addKeyValuePair(key = "Header", value = "this value", isMultiLine = false, isBold = true, fontSize = FontSize.LARGE)
            clientLib.printer.printReceipt(receipt = receiptFormat.generatePaymentReceipt(), callingComponent = this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Note that the Request key will always match the transaction type with "REQUEST_CODE" appended to it.

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GaRequestKeys.PARAMETERS_REQUEST_CODE) {
                val posInfo: PosParameters? = clientLib.getPosParametersResponse(data)
                Log.e("PosInfo", "$posInfo")
            }
            if (requestCode == GaRequestKeys.KEY_EXCHANGE_REQUEST_CODE) {
                Log.e("Key exchange", "Key exchange successful!")
            }
            if (requestCode == GaRequestKeys.CP_PURCHASE_REQUEST_CODE) {
                val cardResponseDetails = clientLib.getCardTransactionResponse(data)
                Log.e("Purchase response", "$cardResponseDetails")
            }
            if (requestCode == GaRequestKeys.MOBILE_MONEY_PURCHASE_REQUEST_CODE) {
                val cardResponseDetails = clientLib.getMobileMoneyTransactionResponse(data)
                Log.e("Purchase response", "$cardResponseDetails")
            }
        }
    }

}