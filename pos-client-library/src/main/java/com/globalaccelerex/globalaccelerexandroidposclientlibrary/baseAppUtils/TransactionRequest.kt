package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.CARD_NOT_PRESENT_TRANSACTION_INTENT
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_REQUEST_DATA_TAG
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_TRANSACTION
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_TRANSACTION_INTENT_ADDRESS
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_PURCHASE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_PURCHASE_WITH_CASH_BACK
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.REQUEST_DATA_TAG
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_REQUEST_INTENT_ADDRESS
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_CARD_BALANCE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_PRE_AUTH_PURCHASE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_REFUND
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.TRANSACTION_TYPE_REVERSAL
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.*
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CNP_PURCHASE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CNP_PURCHASE_WITH_CB_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.MOBILE_MONEY_STATUS_CHECK_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CARD_PURCHASE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CARD_PURCHASE_WITH_CB_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CNP_CARD_BALANCE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CNP_PRE_AUTH_COMPLETION_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CNP_PRE_AUTH_PURCHASE_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.CNP_REFUND_REQUEST_CODE
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.GaResponseKeys.MOBILE_MONEY_PURCHASE_REQUEST_CODE
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
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CARD_PURCHASE_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CARD_PURCHASE_REQUEST_CODE)
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
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CARD_PURCHASE_WITH_CB_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CARD_PURCHASE_WITH_CB_REQUEST_CODE)
        }
    }

    fun performCNPTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentPurchaseRequest(
                transType = TRANSACTION_TYPE_PURCHASE,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CNP_PURCHASE_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CNP_PURCHASE_REQUEST_CODE)
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
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CNP_PURCHASE_WITH_CB_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CNP_PURCHASE_WITH_CB_REQUEST_CODE)
        }
    }

    fun performCNPPreAuthTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentPurchaseRequest(
                transType = TRANSACTION_TYPE_PRE_AUTH_PURCHASE,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_PURCHASE_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_PURCHASE_REQUEST_CODE)
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
                transType = TRANSACTION_TYPE_PRE_AUTH_PURCHASE,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                rrn = reference
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_COMPLETION_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CNP_PRE_AUTH_COMPLETION_REQUEST_CODE)
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
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CNP_CARD_BALANCE_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CNP_CARD_BALANCE_REQUEST_CODE)
        }
    }

    fun performCNPRefundTransactionRequest(
        cardNumber: String,
        expiryDate: String,
        amount: Double,
        callingComponent: Any,
        customPrint: Boolean
    ) {
        val transactionObject =
            CardNotPresentPurchaseRequest(
                transType = TRANSACTION_TYPE_REFUND,
                amount = amount.toPosAmount(),
                print = (!customPrint).toString(),
                cardNumber = cardNumber,
                expiryDate = expiryDate
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(CARD_NOT_PRESENT_TRANSACTION_INTENT)
        intent.putExtra(REQUEST_DATA_TAG, transJson)
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CNP_REFUND_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CNP_REFUND_REQUEST_CODE)
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
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, CNP_REFUND_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, CNP_REFUND_REQUEST_CODE)
        }
    }

    fun performMobileMoneyPurchaseRequest(
        callingComponent: Any,
        mobileOperator: MobileMoneyOperators,
        amount: Double,
        phoneNumber: String
    ) {
        val transactionObject =
            MobileMoneyTransactionRequest(
                amount = amount.toPosAmount(),
                phoneNumber = phoneNumber,
                mobileOperator = mobileOperator.name,
                requestType = MOBILE_MONEY_TRANSACTION
            )
        val transJson = Gson().toJson(transactionObject)
        val intent = Intent(MOBILE_MONEY_TRANSACTION_INTENT_ADDRESS)
        intent.putExtra(MOBILE_MONEY_REQUEST_DATA_TAG, transJson)
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, MOBILE_MONEY_PURCHASE_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, MOBILE_MONEY_PURCHASE_REQUEST_CODE)
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
        if (callingComponent is Fragment) {
            callingComponent.startActivityForResult(intent, MOBILE_MONEY_STATUS_CHECK_REQUEST_CODE)
        } else if (callingComponent is Activity) {
            callingComponent.startActivityForResult(intent, MOBILE_MONEY_STATUS_CHECK_REQUEST_CODE)
        }
    }
}