package com.globalaccelerex.globalaccelerexandroidposclientlibrary

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.*
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.Printer
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.CardNotPresentTransactions
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.CardTransactions
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.MobileMoneyTransactions
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.ParsingUtil
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.RequestStatus
import com.google.gson.Gson

/**
 * Entry point.
 * Instance of this object gives you the ability to make requests to the POS base application.
 * A single instance of this class is to be used in the application scope to make POS transactions.
 * */
class GAClientLib private constructor(
    private val countryCode: Countries
) {

    private val TAG = "GAClientLib"

    private val parametersRequest = ParameterRequest()
    private val keyExchangeRequest = KeyExchangeRequest()

    init {
        /**
         * Terminal information contains configuration to enable required features available in your region.
         * */
        TerminalInformation.setTerminalMode(
            country = countryCode
        )
    }

    data class Builder(
        private var countryCode: Countries? = null
    ) {
        fun setCountryCode(code: Countries) = apply { this.countryCode = code }
        fun build() = GAClientLib(countryCode!!)
    }

    val cardTransactions by lazy { CardTransactions() }
    val cardNotPresentTransactions by lazy { CardNotPresentTransactions(countryCode) }
    val mobileMoneyTransaction by lazy { MobileMoneyTransactions(countryCode) }
    val printer by lazy { Printer() }

    /**
     * This function is used to get the parameter details of the POS device being used.
     * [callingComponent] should be either [Fragment] or [Activity] or their subclasses.
     * */
    fun makeParametersRequest(callingComponent: Any) {
        parametersRequest.performParameterRequest(callingComponent)
    }

    /**
     * This function is used to make key exchange on the POS device being used.
     * [callingComponent] should be either [Fragment] or [Activity] or their subclasses.
     * */
    fun makeKeyExchangeRequest(callingComponent: Any) {
        keyExchangeRequest.performKeyExchange(
            callingComponent = callingComponent
        )
    }

    /**
     * This function should be used in the [onActivityResult] of the calling fragment or activity.
     * It takes in the intent data returned and returns a [PosParameters] if successful.
     *
     * If any error occurs, null is returned.
     * */
    fun getPosParametersResponse(data: Intent?): PosParameters? {
        val status = data?.getStringExtra("status")
        return if (ParsingUtil.getStatus(status) == RequestStatus.SUCCESS) {
            val jsonString = data?.getStringExtra("data")
            Gson().fromJson(jsonString, PosParameters::class.java)
        } else null
    }

    /**
     * This function should be used in the [onActivityResult] of the calling fragment or activity.
     * It takes in the intent data returned and returns a [CardTransactionResponse].
     *
     * If any error occurs, null is returned.
     * */
    fun getCardTransactionResponse(data: Intent?): CardTransactionResponse? {
        return try {
            val status = data?.getStringExtra("status")
            val jsonData = data?.getStringExtra("data")
            val message = data?.getStringExtra("message")
            val cardTransaction = Gson().fromJson(jsonData, CardTransaction::class.java)
            CardTransactionResponse(
                status = ParsingUtil.getStatus(status),
                transactionData = cardTransaction,
                message = message
            )
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "A transaction error occured.")
            null
        }
    }

    /**
     * This function should be used in the [onActivityResult] of the calling fragment or activity.
     * It takes in the intent data returned and returns a [MobileMoneyTransactionResponse].
     *
     * If any error occurs, null is returned.
     * */
    fun getMobileMoneyTransactionResponse(data: Intent?): MobileMoneyTransactionResponse? {
        return try {
            val status = data?.getStringExtra("status")
            val jsonData = data?.getStringExtra("data")
            val mmTransaction =
                Gson().fromJson(jsonData, MobileMoneyTransaction::class.java)
            MobileMoneyTransactionResponse(
                status = ParsingUtil.getStatus(status),
                transactionData = mmTransaction
            )
        } catch (e: Exception) {
            null
        }
    }

}