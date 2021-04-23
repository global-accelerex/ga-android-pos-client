package com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Creates a transaction request
 * @param amount amount of transaction
 * @param printReceipt if you want to print your own custom receipt, set this to false
 */
public sealed class Transaction(
    public open val amount: Double,
    public open val printReceipt: Boolean
) {
    protected val gson: Gson by lazy {
        GsonBuilder()
            .create()
    }

    public abstract fun request(): String

    /**
     * Creates a purchase transaction request.
     */
    internal data class Purchase(override val amount: Double, override val printReceipt: Boolean) :
        Transaction(amount, printReceipt) {
        override fun request(): String {
            val transactionRequest = TransactionRequest(
                amount = amount.toString(),
                printReceipt = printReceipt.toString(),
                transType = TransactionType.PURCHASE.name
            )
            return gson.toJson(transactionRequest)
        }
    }

    /**
     * Purchase with cashback transaction
     * @param cashbackAmount the cashback amount
     * @param amount the amount of transaction (does not include the cashback amount)
     */
    internal data class PurchaseWithCb(
        override val amount: Double,
        val cashbackAmount: Double,
        override val printReceipt: Boolean
    ) : Transaction(amount, printReceipt) {
        override fun request(): String {
            val transactionRequest = TransactionRequest(
                amount = amount.toString(),
                printReceipt = printReceipt.toString(),
                transType = TransactionType.PURCHASEWITHCB.name
            )
            return gson.toJson(transactionRequest)
        }
    }


    public companion object {

        /**
         * Creates a purchase transaction request.
         */
        @JvmStatic
        public fun purchase(amount: Double, printReceipt: Boolean): Transaction =
            Purchase(amount, printReceipt)

        @JvmStatic
        public fun purchaseWithCb(
            amount: Double,
            cashbackAmount: Double,
            printReceipt: Boolean
        ): Transaction = PurchaseWithCb(amount, cashbackAmount, printReceipt)
    }
}
