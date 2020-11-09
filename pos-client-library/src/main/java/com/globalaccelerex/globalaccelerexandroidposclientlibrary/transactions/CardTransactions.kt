package com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions

import android.app.Activity
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TransactionRequest
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.TransactionType

class CardTransactions {

    private val transactionRequest = TransactionRequest()

    /**
     *
     * @throws IllegalArgumentException
     *
     *  This function is used to make card transactions on the POS device.
     *  [callingComponent] should be either [Fragment] or [Activity]
     *
     *  [cashBackAmount] should be present if [transactionType] is set to [TransactionType.CP_PURCHASE_WITH_CB]
     *
     *  @param amount: Amount for the transaction to be made. Amount should always be in [Double] format
     *  @param transactionType: This specifies the type of payment transaction to be carried out. See [TransactionType] for different payment options
     *  @param cashBackAmount: This parameter should only be used when [transactionType] is [TransactionType.CP_PURCHASE_WITH_CB]
     *  @param callingComponent: This represents the class from which the function is called. Calling component must be of [Activity]. Overloaded function handles fragment cases.
     *  @param customPrint: Set this to true if you want to design your own receipt format. If set to false, the default printing format will be used.
     *
     * */
    fun purchase(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        transactionRequest.performCPTransactionRequest(
            callingComponent = callingComponent,
            amount = amount,
            customPrint = customPrint
        )
    }

    fun purchaseWithCashBack(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean,
        cashBackAmount: Double
    ) {
        transactionRequest.performCPCashBackTransactionRequest(
            amount = amount,
            callingComponent = callingComponent,
            customPrint = customPrint,
            cashbackAmount = cashBackAmount
        )
    }



}