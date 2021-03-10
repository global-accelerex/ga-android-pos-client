package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.CARD_NOT_PRESENT_TRANSACTION_INTENT
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_REQUEST_DATA_TAG
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_TRANSACTION
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_TRANSACTION_INTENT_ADDRESS
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.REQUEST_DATA_TAG
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_REQUEST_INTENT_ADDRESS
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_CARD_BALANCE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_PRE_AUTH_PURCHASE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_PRE_AUTH_PURCHASE_COMPLETION
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_PURCHASE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_PURCHASE_WITH_CASH_BACK
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_REFUND
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_REVERSAL
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedCallingComponentException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CP_PURCHASE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CP_PURCHASE_WITH_CASHBACK_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CNP_CARD_BALANCE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CNP_PRE_AUTH_COMPLETION_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CNP_PRE_AUTH_PURCHASE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CNP_PURCHASE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CNP_PURCHASE_WITH_CB_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CNP_REFUND_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CNP_REVERSAL_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CP_PREAUTH_COMPLETION_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CP_PREAUTH_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CP_REFUND_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.CP_REVERSAL_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.MOBILE_MONEY_PURCHASE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaRequestKeys.MOBILE_MONEY_STATUS_CHECK_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.MobileMoneyOperators
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.toPosAmount
import com.google.gson.Gson

internal class TransactionRequest {

    fun performCPTransactionRequest(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            TransactionPurchaseRequest(
                transType = TRANSACTION_TYPE_PURCHASE,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString()
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(TRANSACTION_REQUEST_INTENT_ADDRESS)
        intent.putExtra(REQUEST_DATA_TAG, transJson)


        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CP_PURCHASE_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CP_PURCHASE_REQUEST_CODE)
            }
            else -> {
                throw UnsupportedCallingComponentException("Unsupported calling component.")
            }
        }
    }

    fun performCPCashBackTransactionRequest(
        amount: Double,
        callingComponent: Any,
        cashbackAmount: Double,
        customPrint: Boolean
    ) {
        val transactionObject =
            TransactionPurchaseWithCashBackRequest(
                transType = TRANSACTION_TYPE_PURCHASE_WITH_CASH_BACK,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cashBackAmount = cashbackAmount.toPosAmount()
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(TRANSACTION_REQUEST_INTENT_ADDRESS)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CP_PURCHASE_WITH_CASHBACK_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CP_PURCHASE_WITH_CASHBACK_REQUEST_CODE)
            }
            else ->  throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCPReversalRequest(
        amount: Double,
        rrn: String,
        customPrint: Boolean,
        callingComponent: Any
    ) {
        val transactionObject = CardReversalTransactionObject(
            amount = amount.toPosAmount(),
            rrn = rrn,
            print = (!customPrint).toString(),
            transType = TRANSACTION_TYPE_REVERSAL
        )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(TRANSACTION_REQUEST_INTENT_ADDRESS)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CP_REVERSAL_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CP_REVERSAL_REQUEST_CODE)
            }
            else ->  throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCPRefundRequest(
        amount: Double,
        rrn: String,
        customPrint: Boolean,
        callingComponent: Any
    ) {
        val transactionObject = CardReversalTransactionObject(
            amount = amount.toPosAmount(),
            rrn = rrn,
            print = (!customPrint).toString(),
            transType = TRANSACTION_TYPE_REFUND
        )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(TRANSACTION_REQUEST_INTENT_ADDRESS)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CP_REFUND_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CP_REFUND_REQUEST_CODE)
            }
            else ->  throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCPPreAuthTransaction(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            TransactionPurchaseRequest(
                transType = TRANSACTION_TYPE_PRE_AUTH_PURCHASE,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString()
            )

        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(TRANSACTION_REQUEST_INTENT_ADDRESS)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CP_PREAUTH_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CP_PREAUTH_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCPPreAuthCompletionTransaction(
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean,
        reference: String
    ) {
        val transactionObject =
            CardTransactionPreAuthCompletionRequest(
                transType = TRANSACTION_TYPE_PRE_AUTH_PURCHASE_COMPLETION,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                reference = reference
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(TRANSACTION_REQUEST_INTENT_ADDRESS)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CP_PREAUTH_COMPLETION_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CP_PREAUTH_COMPLETION_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCNPTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        cvv: String,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentPurchaseRequest(
                transType = TRANSACTION_TYPE_PURCHASE,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                cvv = cvv,
                expiryDate = expiryDate
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CNP_PURCHASE_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CNP_PURCHASE_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCNPCashBackTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        callingComponent: Any,
        cashbackAmount: Double,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentCashBackRequest(
                transType = TRANSACTION_TYPE_PURCHASE_WITH_CASH_BACK,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cashBackAmount = cashbackAmount.toPosAmount(),
                cardNumber = cardNumber,
                expiryDate = expiryDate
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CNP_PURCHASE_WITH_CB_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CNP_PURCHASE_WITH_CB_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCNPPreAuthTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        cvv: String,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentPurchaseRequest(
                transType = TRANSACTION_TYPE_PRE_AUTH_PURCHASE,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                cvv = cvv
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_PURCHASE_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_PURCHASE_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCNPPreAuthCompletionTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        reference: String,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentPreAuthCompletionRequest(
                transType = TRANSACTION_TYPE_PRE_AUTH_PURCHASE_COMPLETION,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                rrn = reference
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_COMPLETION_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_COMPLETION_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCNPCardBalanceTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentCardBalanceRequest(
                transType = TRANSACTION_TYPE_CARD_BALANCE,
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CNP_CARD_BALANCE_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CNP_CARD_BALANCE_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCNPRefundTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        cvv: String,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentPurchaseRequest(
                transType = TRANSACTION_TYPE_REFUND,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                cvv = cvv
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CNP_REFUND_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CNP_REFUND_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performCNPReversalTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean,
        reference: String
    ) {
        val transactionObject =
            CardNotPresentReversalRequest(
                transType = TRANSACTION_TYPE_REVERSAL,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                rrn = reference
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, CNP_REVERSAL_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, CNP_REVERSAL_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performMobileMoneyPurchaseRequest(
        callingComponent: Any,
        mobileOperator: MobileMoneyOperators,
        amount: Double,
        phoneNumber: String,
        timeout: Int?
    ) {
        val transactionObject =
            MobileMoneyTransactionRequest(
                amount = amount.toPosAmount(),
                phoneNumber = phoneNumber,
                mobileOperator = mobileOperator.name,
                requestType = MOBILE_MONEY_TRANSACTION,
                timeout = timeout
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(MOBILE_MONEY_TRANSACTION_INTENT_ADDRESS)
        intent.putExtra(MOBILE_MONEY_REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, MOBILE_MONEY_PURCHASE_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, MOBILE_MONEY_PURCHASE_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }

    fun performMobileMoneyStatusCheckerRequest(
        rrn: String,
        stan: String,
        timeStamp: String,
        callingComponent: Any
    ){
        val transactionObject =
            MobileMoneyRequeryRequest(
                rrn = rrn,
                stan = stan,
                transDateTime = timeStamp
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(MOBILE_MONEY_TRANSACTION_INTENT_ADDRESS)
        intent.putExtra(MOBILE_MONEY_REQUEST_DATA_TAG, transJson)
        when (callingComponent) {
            is Fragment -> {
                callingComponent.startActivityForResult(intent, MOBILE_MONEY_STATUS_CHECK_REQUEST_CODE)
            }
            is Activity -> {
                callingComponent.startActivityForResult(intent, MOBILE_MONEY_STATUS_CHECK_REQUEST_CODE)
            }
            else -> throw UnsupportedCallingComponentException("Unsupported calling component.")
        }
    }
}