package com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions

import android.app.Activity
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TransactionRequest
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedFeatureException

class CardTransactions {

    private val transactionRequest = TransactionRequest()

    /**
     *
     * @throws IllegalArgumentException
     *
     *  This function is used to make card transactions on the POS device.
     *  [callingComponent] should be either [Fragment] or [Activity]
     *
     *  @param amount: Amount for the transaction to be made. Amount should always be in [Double] format
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

    /**
     *
     *  @throws IllegalArgumentException if [cashBackAmount] is greater than [amount]
     *
     *  This function is used to make card transactions on the POS device.
     *  [callingComponent] should be either [Fragment] or [Activity]
     *
     *  @param amount: Amount for the transaction to be made. Amount should always be in [Double] format
     *  @param callingComponent: This represents the class from which the function is called. Calling component must be of [Activity]. Overloaded function handles fragment cases.
     *  @param customPrint: Set this to true if you want to design your own receipt format. If set to false, the default printing format will be used.
     *  @param cashBackAmount: This represents the cashback amount and it should be less than or equal to the transaction amount.
     * */
    fun purchaseWithCashBack(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean,
        cashBackAmount: Double
    ) {
        require(amount >= cashBackAmount) { "Cash back amount cannot be greater than transaction amount" }
        transactionRequest.performCPCashBackTransactionRequest(
            amount = amount,
            callingComponent = callingComponent,
            customPrint = customPrint,
            cashbackAmount = cashBackAmount
        )
    }

    /**
     * This method is used to make card refund transactions.
     * @param amount is the cost of the transaction to be made.
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * @param rrn is the Retrieval Reference Number of the transaction to be reversed.
     * */
    fun reversal(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean,
        rrn: String
    ) {
        transactionRequest.performCPReversalRequest(
            amount = amount,
            rrn = rrn,
            callingComponent = callingComponent,
            customPrint = customPrint
        )
    }

    /**
     * This method is used to make card refund transactions.
     * @param amount is the cost of the transaction to be made.
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * @param rrn is the Retrieval Reference Number of the transaction to be refunded.
     * */
    fun refund(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean,
        rrn: String
    ) {
        transactionRequest.performCPRefundRequest(
            amount = amount,
            rrn = rrn,
            callingComponent = callingComponent,
            customPrint = customPrint
        )
    }

}