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

class MainActivity : AppCompatActivity() {

    private val clientLib = GAClientLib.Builder()
        .setCountryCode(Countries.NIGERIA)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paramter_request.setOnClickListener {
            clientLib.makeParametersRequest(this)
        }

        key_exchange.setOnClickListener {
            clientLib.makeKeyExchangeRequest(this)
        }
        transaction_request.setOnClickListener {
            clientLib.cardTransactions.purchase(
                amount = 0.01,
                callingComponent = this,
                customPrint = true
            )
        }
        mobile_money_request.setOnClickListener {
            clientLib.mobileMoneyTransaction.purchase(
                phoneNumber = "08143051805",
                amount = 1.0,
                mobileOperator = MobileMoneyOperators.MTN,
                callingComponent = this
            )
        }
        cnp_purchase.setOnClickListener {
            clientLib.cardNotPresentTransactions.purchase(
                cardNumber = "5196010125746208",
                cardExpiryDate = "2302",
                callingComponent = this,
                amount = 1.0,
                customPrint = false
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GaResponseKeys.PARAMETERS_REQUEST_CODE) {
                val posInfo: PosInformation? = clientLib.getPosParametersResponse(data)
                Log.e("PosInfo", "$posInfo")
            }
            if (requestCode == GaResponseKeys.CNP_PURCHASE_REQUEST_CODE) {
                val posInfo: PosInformation? = clientLib.getPosParametersResponse(data)
                Log.e("PosInfo", "$posInfo")
            }
            if (requestCode == GaResponseKeys.KEY_EXCHANGE_REQUEST_CODE) {
                Log.e("Key exchange", "Key exchange successful!")
            }
            if (requestCode == GaResponseKeys.CARD_PURCHASE_REQUEST_CODE) {
                val cardResponseDetails = clientLib.getCardTransactionResponse(data)
                Log.e("Purchase response", "$cardResponseDetails")
            }
            if (requestCode == GaResponseKeys.MOBILE_MONEY_PURCHASE_REQUEST_CODE) {
                val cardResponseDetails = clientLib.getCardTransactionResponse(data)
                Log.e("Purchase response", "$cardResponseDetails")
            }
        }
    }

}