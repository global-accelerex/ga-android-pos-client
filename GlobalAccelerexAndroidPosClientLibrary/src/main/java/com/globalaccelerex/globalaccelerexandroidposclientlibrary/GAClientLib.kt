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
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.*
import com.google.gson.Gson

/**
 * Entry point.
 * Instance of this object gives you the ability to make requests to the POS application.
 * */

class GAClientLib private constructor(
    private val countryCode: Countries
) {

    private var hasPerformedKeyExchange: Boolean = false
    private val TAG = "GAClientLib"
    private val parametersRequest = ParameterRequest()
    private val keyExchangeRequest = KeyExchangeRequest()
    private val transactionRequest = TransactionRequest()

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
     *  @param callingComponent: This represents the class from which the function is called. Calling component must be of [Fragment] or [Activity]. Overloaded function handles activity cases.
     *  @param customPrint: Set this to true if you want to design your own receipt format. If set to false, the default printing format will be used.
     *
     * */
    fun makeCardPresentTransactionRequest(
        amount: Double,
        transactionType: TransactionType,
        callingComponent: Fragment,
        cashBackAmount: Double? = null,
        customPrint: Boolean
    ) {
        require(transactionType == TransactionType.CP_PURCHASE || transactionType == TransactionType.CP_PURCHASE_WITH_CB) {
            throw IllegalArgumentException(
                "transactactionType should start with CP indicating CARD PRESENT."
            )
        }

        when (transactionType) {
            TransactionType.CP_PURCHASE -> {
                transactionRequest.performCPTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    customPrint = customPrint
                )
            }
            TransactionType.CP_PURCHASE_WITH_CB -> {
                requireNotNull(cashBackAmount) { throw IllegalArgumentException("Cashback parameter cannot be null for Cashback Purchase transactions") }
                transactionRequest.performCPCashBackTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    cashbackAmount = cashBackAmount,
                    customPrint = customPrint
                )
            }
        }
    }

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
    fun makeCardPresentTransactionRequest(
        amount: Double,
        transactionType: TransactionType,
        callingComponent: Activity,
        cashBackAmount: Double? = null,
        customPrint: Boolean
    ) {
        require(transactionType == TransactionType.CP_PURCHASE || transactionType == TransactionType.CP_PURCHASE_WITH_CB) {
            throw IllegalArgumentException(
                "transactactionType should start with CP indicating CARD PRESENT."
            )
        }
        when (transactionType) {
            TransactionType.CP_PURCHASE -> {
                transactionRequest.performCPTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    customPrint = customPrint
                )
                transactionRequest.performCPTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    customPrint = customPrint
                )
            }

            TransactionType.CP_PURCHASE_WITH_CB -> {
                requireNotNull(cashBackAmount) { throw IllegalArgumentException("CashBack parameter cannot be null for CashBack Purchase transactions") }
                transactionRequest.performCPCashBackTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    cashbackAmount = cashBackAmount,
                    customPrint = customPrint
                )

                transactionRequest.performCPCashBackTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    cashbackAmount = cashBackAmount,
                    customPrint = customPrint
                )
            }
        }
    }

    /**
     * This function helps to make mobile money transactions
     * @throws UnsupportedFeatureException if this is called with a country configuration != [Countries.GHANA]
     * @throws IllegalArgumentException if called from a class which is not of [Activity] of [Fragment] types.
     * @param transType: This is the type of mobile money transaction to be made. This is of type [TransactionType]
     * @param mobileOperator: This is the mobile operator for the mobile money operation. It should be of type [MobileMoneyOperators]
     * @param amount: The amount to be paid
     * @param callingComponent: This is the class from which the function is called. Should be [Activity] or [Fragment]
     * * */
    fun makeMobileMoneyTransactionRequest(
        mobileOperator: MobileMoneyOperators,
        amount: Double,
        phoneNumber: String,
        callingComponent: Fragment
    ) {
        require(countryCode == Countries.GHANA) { throw UnsupportedFeatureException("This feature is not available in your specified country.") }

        transactionRequest.performMobileMoneyPurchaseRequest(
            callingComponent = callingComponent,
            mobileOperator = mobileOperator,
            amount = amount,
            phoneNumber = phoneNumber
        )

    }


    fun makeMobileMoneyTransactionRequest(
        mobileOperator: MobileMoneyOperators,
        amount: Double,
        phoneNumber: String,
        callingComponent: Activity
    ) {
        require(countryCode == Countries.GHANA) { throw UnsupportedFeatureException("This feature is not available in your specified country.") }
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
    fun makeMobileMoneyStatusCheckRequest(
        rrn: String,
        stan: String,
        timeStamp: String,
        callingComponent: Activity
    ) {
        require(countryCode == Countries.GHANA) { throw UnsupportedFeatureException("This feature is not available in your specified country.") }
        transactionRequest.performMobileMoneyStatusCheckerRequest(
            rrn = rrn,
            stan = stan,
            timeStamp = timeStamp,
            callingComponent = callingComponent
        )
    }

    /**
     * This is used to perform mobile money status check or re-query transactions.
     * @param rrn: This is the RRN (Retrival Reference Number) of the transaction to be queried.
     * @param stan: This is the stan of the transaction to be queried. Usually in the initial mobile money transaction.
     * @param timeStamp: This is the [dateTime] on the [MobileMoneyTransaction] response. The required date format for this query is "MMddHHmmss"
     * @param callingComponent: This is the class from which the function is called. It should be either an [Activity] or a [Fragment]
     * */
    fun makeMobileMoneyStatusCheckRequest(
        rrn: String,
        stan: String,
        timeStamp: String,
        callingComponent: Fragment
    ) {
        require(countryCode == Countries.GHANA) { throw UnsupportedFeatureException("This feature is not available in your specified country.") }
        transactionRequest.performMobileMoneyStatusCheckerRequest(
            rrn = rrn,
            stan = stan,
            timeStamp = timeStamp,
            callingComponent = callingComponent
        )
    }

    /**
     * This method is used to make card not present purchases.
     * This function should only be used with Kenyan configuration.
     * @throws UnsupportedFeatureException if another country configuration is used.
     * @param cardNumber This is the card pan of the card to be used for the CNP transaction
     * @param cardExpiryDate The expiry date of the card to be used for the CNP transaction
     * @param
     * */

    fun makeCardNotPresentTransactionRequest(
        cardNumber: String,
        cardExpiryDate: String,
        transactionType: TransactionType,
        amount: Double? = null,
        cashBackAmount: Double? = null,
        reference: String? = null,
        customPrint: Boolean,
        callingComponent: Activity
    ) {
        require(countryCode == Countries.KENYA) { throw UnsupportedFeatureException("This feature is not supported in specified country") }
        require(transactionType == TransactionType.CNP_PURCHASE ||
                transactionType == TransactionType.CNP_PURCHASE_WITH_CASH_BACK ||
                transactionType == TransactionType.CNP_PRE_AUTH ||
                transactionType == TransactionType.CNP_PRE_AUTH_COMPLETION) {
            throw IllegalArgumentException(
                "transactactionType should start with CNP indicating CARD NOT PRESENT."
            )
        }
        when (transactionType) {
            TransactionType.CNP_PURCHASE -> {
                requireNotNull(amount) { throw IllegalArgumentException("Amount cannot be null to make this transaction type.")}
                transactionRequest.performCNPTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    customPrint = customPrint,
                    cardNumber = cardNumber,
                    expiryDate = cardExpiryDate
                )
            }

            TransactionType.CNP_PURCHASE_WITH_CASH_BACK -> {
                requireNotNull(amount) { throw IllegalArgumentException("Amount cannot be null to make this transaction type.")}
                requireNotNull(cashBackAmount) { throw IllegalArgumentException("CashBack parameter cannot be null for CashBack Purchase transactions") }
                transactionRequest.performCNPCashBackTransactionRequest(
                    callingComponent = callingComponent,
                    amount = amount,
                    cashbackAmount = cashBackAmount,
                    customPrint = customPrint,
                    cardNumber = cardNumber,
                    expiryDate = cardExpiryDate
                )
            }

            TransactionType.CNP_PRE_AUTH -> {
                requireNotNull(amount) { throw IllegalArgumentException("Amount cannot be null to make this transaction type.")}
                transactionRequest.performCNPPreAuthTransactionRequest(
                    cardNumber = cardNumber,
                    expiryDate = cardExpiryDate,
                    amount = amount,
                    customPrint = customPrint,
                    callingComponent = callingComponent
                )
            }

            TransactionType.CNP_PRE_AUTH_COMPLETION -> {
                requireNotNull(amount) { throw IllegalArgumentException("Amount cannot be null to make this transaction type.")}
                requireNotNull(reference) {throw IllegalArgumentException("Reference number cannot be null for a Pre auth completion transaction.")}
                transactionRequest.performCNPPreAuthCompletionTransactionRequest(
                    cardNumber = cardNumber,
                    expiryDate = cardExpiryDate,
                    amount = amount,
                    customPrint = customPrint,
                    callingComponent = callingComponent,
                    reference = reference
                )
            }

            TransactionType.CNP_CARD_BALANCE -> {
                transactionRequest.performCNPCardBalanceTransactionRequest(
                    cardNumber = cardNumber,
                    expiryDate = cardExpiryDate,
                    callingComponent = callingComponent,
                    customPrint = customPrint
                )
            }

            TransactionType.CNP_REFUND -> {
                requireNotNull(amount) { throw IllegalArgumentException("Amount cannot be null to make this transaction type.")}
                transactionRequest.performCNPRefundTransactionRequest(
                    cardNumber = cardNumber,
                    expiryDate = cardExpiryDate,
                    amount = amount,
                    customPrint = customPrint,
                    callingComponent = callingComponent
                )
            }

            TransactionType.CNP_REVERSAL -> {
                requireNotNull(amount) { throw IllegalArgumentException("Amount cannot be null to make this transaction type.")}
                requireNotNull(reference) {throw IllegalArgumentException("Reference number cannot be null for a Pre auth completion transaction.")}
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