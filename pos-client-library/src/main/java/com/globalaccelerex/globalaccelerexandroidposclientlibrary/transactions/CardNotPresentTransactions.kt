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
     * This function should only be used with Kenya or Ghana configurations.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @throws IllegalArgumentException if card number is less than 12 digits or greater than 18 digits
     * @throws IllegalArgumentException if card expiry date exceeds 4 digits
     * @param amount is the cost of the transaction to be made.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction.
     *          The format of the expiry date should be yyMM. Example: March 2020 will be '2003'
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
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

    /**
     * This method is used to make card not present purchases.
     * This function should only be used with Kenya or Ghana configurations.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @throws IllegalArgumentException if card number is less than 12 digits or greater than 18 digits
     * @throws IllegalArgumentException if card expiry date exceeds 4 digits
     * @throws IllegalArgumentException if [cashBackAmount] is greater than [amount]
     * @param amount is the cost of the transaction to be made.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction.
     *          The format of the expiry date should be yyMM. Example: March 2020 will be '2003'
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * @param cashBackAmount This is your cashback amount and it should be less than or equal to the [amount]
     * */
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

    /**
     * This method is used to make card not present purchases.
     * This function should only be used with Kenya or Ghana configurations.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @throws IllegalArgumentException if card number is less than 12 digits or greater than 18 digits
     * @throws IllegalArgumentException if card expiry date exceeds 4 digits
     * @param amount is the cost of the transaction to be made.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction.
     *          The format of the expiry date should be yyMM. Example: March 2020 will be '2003'
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * */
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

    /**
     * This method is used to make card not present purchases.
     * This function should only be used with Kenya or Ghana configurations.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @throws IllegalArgumentException if card number is less than 12 digits or greater than 18 digits
     * @throws IllegalArgumentException if card expiry date exceeds 4 digits
     * @param amount is the cost of the transaction to be made.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction.
     *          The format of the expiry date should be yyMM. Example: March 2020 will be '2003'
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * @param reference is the reference to the pre-auth transaction to be completed.
     * */
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

    /**
     * This method is used to make card not present card balance check.
     * This function should only be used with Kenya or Ghana configurations.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @throws IllegalArgumentException if card number is less than 12 digits or greater than 18 digits
     * @throws IllegalArgumentException if card expiry date exceeds 4 digits
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction.
     *          The format of the expiry date should be yyMM. Example: March 2020 will be '2003'
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * */
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

    /**
     * This method is used to make card not present refund transactions.
     * This function should only be used with Kenya or Ghana configurations.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @throws IllegalArgumentException if card number is less than 12 digits or greater than 18 digits
     * @throws IllegalArgumentException if card expiry date exceeds 4 digits
     * @param amount is the cost of the transaction to be made.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction.
     *          The format of the expiry date should be yyMM. Example: March 2020 will be '2003'
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * */
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

    /**
     * This method is used to make card not present reversal transactions.
     * This function should only be used with Kenya or Ghana configurations.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @throws IllegalArgumentException if card number is less than 12 digits or greater than 18 digits
     * @throws IllegalArgumentException if card expiry date exceeds 4 digits
     * @param amount is the cost of the transaction to be made.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction.
     *          The format of the expiry date should be yyMM. Example: March 2020 will be '2003'
     * @param customPrint This field indicates if you want to handle the printing of the transaction details by yourself or
     *         use the POS default.
     * @param callingComponent The calling component is the class from which this method is called.
     *          It should be of type [Activity], [Fragment] or their subclasses.
     * @param reference is a Reference to the transaction to be reversed.
     * */
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






















