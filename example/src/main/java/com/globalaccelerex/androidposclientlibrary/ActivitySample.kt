package com.globalaccelerex.androidposclientlibrary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.GAClientLib
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.PosInformation
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This activity shows an example of how each library function is used.
 * Same principles apply when calling from a fragment
 * */

class ActivitySample : AppCompatActivity() {

    private val clientLib = GAClientLib.Builder()
        .setCountryCode(Countries.NIGERIA)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make Key Exchange
        key_exchange.setOnClickListener {
            clientLib.makeKeyExchangeRequest(this)
        }

        //Make parameter request
        paramter_request.setOnClickListener {
            clientLib.makeParametersRequest(this)
        }

        //Make Card Present Transactions (CP)
        transaction_request.setOnClickListener {
            clientLib.makeCardPresentTransactionRequest(
                amount = 1.0,
                customPrint = true,
                transactionType = TransactionType.CP_PURCHASE,
                callingComponent = this
            )
        }

        //Make Card Not Present Transactions (CNP)
        cnp_purchase.setOnClickListener {
            clientLib.makeCardNotPresentTransactionRequest(
                cardNumber = "5196010125746208",
                cardExpiryDate = "2302",
                callingComponent = this,
                amount = 1.0,
                transactionType = TransactionType.CNP_PURCHASE,
                customPrint = false
            )
        }

        //Make Mobile Money Transactions (MM)
        mobile_money_request.setOnClickListener {
            clientLib.makeMobileMoneyTransactionRequest(
                phoneNumber = "08143051805",
                amount = 1.0,
                mobileOperator = MobileMoneyOperators.MTN,
                callingComponent = this
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Note that the Request key will always match the transaction type with "REQUEST_CODE" appended to it.

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GaRequestKeys.KEY_EXCHANGE_REQUEST_CODE) {
                Log.e("Key exchange", "Key exchange successful!")
            }
            if (requestCode == GaRequestKeys.PARAMETERS_REQUEST_CODE) {
                val posInfo: PosInformation? = clientLib.getPosParametersResponse(data)
                Log.e("PosInfo", "$posInfo")
            }
            if (requestCode == GaRequestKeys.CP_PURCHASE_REQUEST_CODE) {
                val cardResponseDetails = clientLib.getCardTransactionResponse(data)
                Log.e("Purchase response", "$cardResponseDetails")
            }
            if (requestCode == GaRequestKeys.CNP_PURCHASE_REQUEST_CODE) {
                // CNP Response type hasn't been defined yet
            }
            if (requestCode == GaRequestKeys.MOBILE_MONEY_PURCHASE_REQUEST_CODE) {
                val mobileMoneyTransactionResponse = clientLib.getMobileMoneyTransactionResponse(data)
                Log.e("Purchase response", "$mobileMoneyTransactionResponse")
            }
        }
    }

}