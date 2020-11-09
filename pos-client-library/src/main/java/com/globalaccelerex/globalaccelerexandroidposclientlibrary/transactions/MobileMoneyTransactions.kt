package com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions

import android.app.Activity
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.MobileMoneyTransaction
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TransactionRequest
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedFeatureException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.MobileMoneyOperators

class MobileMoneyTransactions(countryCode: Countries) {

    private val transactionRequest = TransactionRequest()

    init {
        require(countryCode == Countries.KENYA) {
            throw UnsupportedFeatureException("This feature is not available in your specified country.")
        }
    }

    fun purchase(
        mobileOperator: MobileMoneyOperators,
        amount: Double,
        phoneNumber: String,
        callingComponent: Any
    ) {
        transactionRequest.performMobileMoneyPurchaseRequest(
            callingComponent = callingComponent,
            mobileOperator = mobileOperator,
            amount = amount,
            phoneNumber = phoneNumber
        )
    }

    /**
     * This is used to perform mobile money status check or re-query transactions.
     * @param rrn: This is the RRN (Retrival Reference Number) of the transaction to be queried.
     * @param stan: This is the stan of the transaction to be queried. Usually in the initial mobile money transaction.
     * @param timeStamp: This is the [dateTime] on the [MobileMoneyTransaction] response. The required date format for this query is "MMddHHmmss"
     * @param callingComponent: This is the class from which the function is called. It should be either an [Activity] or a [Fragment]
     * */
    fun statusCheck(
        rrn: String,
        stan: String,
        timeStamp: String,
        callingComponent: Any
    ) {
        transactionRequest.performMobileMoneyStatusCheckerRequest(
            rrn = rrn,
            stan = stan,
            timeStamp = timeStamp,
            callingComponent = callingComponent
        )
    }
}