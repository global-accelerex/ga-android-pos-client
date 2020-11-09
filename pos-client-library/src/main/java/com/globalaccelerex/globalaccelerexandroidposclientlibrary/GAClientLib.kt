package com.globalaccelerex.globalaccelerexandroidposclientlibrary

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.*
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.FAILURE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.SUCCESS
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.KeyExchangeRequest
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.ParameterRequest
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TerminalInformation
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TransactionRequest
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedFeatureException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.CardNotPresentTransactions
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.CardTransactions
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.MobileMoneyTransactions
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.*
import com.google.gson.Gson

/**
 * Entry point.
 * Instance of this object gives you the ability to make requests to the POS application.
 * A single instance of this class is to be used in the application scope to make POS transactions.
 * */
class GAClientLib private constructor(
    private val countryCode: Countries
) {

    //variable to keep track of when key exchange has been made in the app.
    private var hasPerformedKeyExchange: Boolean = false
    private val TAG = "GAClientLib"

    private val parametersRequest = ParameterRequest()
    private val keyExchangeRequest = KeyExchangeRequest()

    init {
        /**
         * Terminal parameters are specified to enable compatibility with all our
         * pos devices and countries supported
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

    /**
     * This function is used to get the parameter details of the POS device being used.
     * [callingComponent] should be either [Fragment] or [Activity]
     * */
    fun makeParametersRequest(callingComponent: Activity) {
        makeKeyExchangeWarning()
        parametersRequest.performParameterRequest(callingComponent)
    }

    /**
     * This function is used to get the parameter details of the POS device being used.
     * [callingComponent] should be either [Fragment] or [Activity]
     * */
    fun makeParametersRequest(callingComponent: Fragment) {
        makeKeyExchangeWarning()
        parametersRequest.performParameterRequest(callingComponent)
    }


    /**
     * This function is used to make key exchange on the POS device being used.
     * [callingComponent] should be either [Fragment] or [Activity]
     * */
    fun makeKeyExchangeRequest(callingComponent: Activity) {
        this.hasPerformedKeyExchange = true
        keyExchangeRequest.performKeyExchange(
            callingComponent = callingComponent
        )
    }

    /**
     * This function is used to make key exchange on the POS device being used.
     * [callingComponent] should be either [Fragment] or [Activity]
     * */
    fun makeKeyExchangeRequest(callingComponent: Fragment) {
        this.hasPerformedKeyExchange = true
        keyExchangeRequest.performKeyExchange(
            callingComponent = callingComponent
        )
    }

    /**
     * This function should be used in the [onActivityResult] of the calling fragment or activity.
     * It takes in the intent data returned and returns a [PosInformation] if successful.
     *
     * If any error occurs, null is returned.
     * */
    fun getPosParametersResponse(data: Intent?): PosInformation? {
        return if (data?.getStringExtra("status") == SUCCESS) {
            val jsonString = data.getStringExtra("data")!!
            Gson().fromJson(jsonString, PosInformation::class.java)
        } else null
    }

    /**
     * This function should be used in the [onActivityResult] of the calling fragment or activity.
     * It takes in the intent data returned and returns a [CardTransactionResponse].
     *
     * */
    fun getCardTransactionResponse(data: Intent?): CardTransactionResponse? {
        return try {
            val status = data?.getStringExtra("status")
            val jsonData = data?.getStringExtra("data")
            when (status) {
                SUCCESS, FAILURE -> {
                    val cardTransaction = Gson().fromJson(jsonData, CardTransaction::class.java)
                    CardTransactionResponse(
                        status = status,
                        transactionData = cardTransaction
                    )
                }
                else -> null
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "A transaction error occured.")
            null
        }
    }

    /**
     * This function should be used in the [onActivityResult] of the calling fragment or activity.
     * It takes in the intent data returned and returns a [MobileMoneyTransactionResponse].
     *
     * */
    fun getMobileMoneyTransactionResponse(data: Intent?): MobileMoneyTransactionResponse? {
        return try {
            val status = data?.getStringExtra("status")
            val jsonData = data?.getStringExtra("data")
            when (status) {
                SUCCESS, FAILURE -> {
                    val mmTransaction =
                        Gson().fromJson(jsonData, MobileMoneyTransaction::class.java)
                    MobileMoneyTransactionResponse(
                        status = status,
                        transactionData = mmTransaction
                    )
                }
                else -> null
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "A transaction error occured.")
            null
        }
    }

    /**
     * Warning: Key exchange must be made at least once in the application's lifecycle
     * */
    private fun makeKeyExchangeWarning() {
        if (!hasPerformedKeyExchange) {
            Log.d(
                TAG,
                "It seems key exchange hasn't been made during this session. Make sure that is done at least once in the application's lifecycle"
            )
        }
    }

}