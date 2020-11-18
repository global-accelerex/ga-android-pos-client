package com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.TransactionRequest
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedFeatureException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries

class CardNotPresentTransactions(countryCode: Countries) {

    private val transactionRequest = TransactionRequest()

    init {
        require(countryCode == Countries.KENYA || countryCode == Countries.GHANA) {
            throw UnsupportedFeatureException(
                "CNP transactions are not allowed in the configured country."
            )
        }
    }

    /**
     * This method is used to make card not present purchases.
     * This function should only be used with Kenyan configuration.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction
     * @param
     * */
    fun purchase(
        amount: Double,
        cardNumber: String,
        cardExpiryDate: String,
        customPrint: Boolean,
        callingComponent: Any
    ) {
        require(cardNumber.length in 12..18) { "Card Number should be between 12 and 18 digits" }
        require(cardExpiryDate.length == 4) { "Card expiry should be of format yyMM" }
        transactionRequest.performCNPTransactionRequest(
            cardNumber = cardNumber,
            amount = amount,
            expiryDate = cardExpiryDate,
            customPrint = customPrint,
            callingComponent = callingComponent
        )
    }

    fun purchaseWithCashBack(
        amount: Double,
        cashBackAmount: Double,
        cardNumber: String,
        cardExpiryDate: String,
        customPrint: Boolean,
        callingComponent: Any
    ) {
        require(cardNumber.length in 12..18) { "Card Number should be between 12 and 18 digits" }
        require(cardExpiryDate.length == 4) { "Card expiry should be of format yyMM"}
        require(amount >= cashBackAmount) { "Cash back amount cannot be greater than transaction amount" }
        transactionRequest.performCNPCashBackTransactionRequest(
            cardNumber = cardNumber,
            expiryDate = cardExpiryDate,
            cashbackAmount = cashBackAmount,
            amount = amount,
            customPrint = customPrint,
            callingComponent = callingComponent
        )
    }

    fun preAuthPurchase(
        amount: Double,
        cardNumber: String,
        cardExpiryDate: String,
        customPrint: Boolean,
        callingComponent: Any
    ) {
        require(cardNumber.length in 12..18) { "Card Number should be between 12 and 18 digits" }
        require(cardExpiryDate.length == 4) { "Card expiry should be of format yyMM" }
        transactionRequest.performCNPPreAuthTransactionRequest(
            cardNumber = cardNumber,
            expiryDate = cardExpiryDate,
            amount = amount,
            customPrint = customPrint,
            callingComponent = callingComponent
        )
    }

    fun preAuthCompletion(
        amount: Double,
        cardNumber: String,
        cardExpiryDate: String,
        customPrint: Boolean,
        callingComponent: Any,
        reference: String
    ) {
        require(cardNumber.length in 12..18) { "Card Number should be between 12 and 18 digits" }
        require(cardExpiryDate.length == 4) { "Card expiry should be of format yyMM" }
        transactionRequest.performCNPPreAuthCompletionTransactionRequest(
            cardNumber = cardNumber,
            expiryDate = cardExpiryDate,
            amount = amount,
            customPrint = customPrint,
            callingComponent = callingComponent,
            reference = reference
        )
    }

    fun cardBalance(
        cardNumber: String,
        cardExpiryDate: String,
        customPrint: Boolean,
        callingComponent: Any
    ) {
        require(cardNumber.length in 12..18) { "Card Number should be between 12 and 18 digits" }
        require(cardExpiryDate.length == 4) { "Card expiry should be of format yyMM" }
        transactionRequest.performCNPCardBalanceTransactionRequest(
            cardNumber = cardNumber,
            expiryDate = cardExpiryDate,
            callingComponent = callingComponent,
            customPrint = customPrint
        )
    }

    fun refund(
        amount: Double,
        cardNumber: String,
        cardExpiryDate: String,
        customPrint: Boolean,
        callingComponent: Any
    ) {
        require(cardNumber.length in 12..18) { "Card Number should be between 12 and 18 digits" }
        require(cardExpiryDate.length == 4) { "Card expiry should be of format yyMM" }
        transactionRequest.performCNPRefundTransactionRequest(
            cardNumber = cardNumber,
            expiryDate = cardExpiryDate,
            amount = amount,
            customPrint = customPrint,
            callingComponent = callingComponent
        )
    }

    fun reversal(
        amount: Double,
        cardNumber: String,
        cardExpiryDate: String,
        customPrint: Boolean,
        callingComponent: Any,
        reference: String
    ) {
        require(cardNumber.length in 12..18) { "Card Number should be between 12 and 18 digits" }
        require(cardExpiryDate.length == 4) { "Card expiry should be of format yyMM" }
        transactionRequest.performCNPReversalTransactionRequest(
            cardNumber = cardNumber,
            expiryDate = cardExpiryDate,
            amount = amount,
            reference = reference,
            callingComponent = callingComponent,
            customPrint = customPrint
        )
    }
}






















