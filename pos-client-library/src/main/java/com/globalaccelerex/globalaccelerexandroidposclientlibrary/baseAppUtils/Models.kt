package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import android.os.Parcelable
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_STATUS_CHECK
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.RequestStatus
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PosParameters(
        @SerializedName("BankLogo")
        val bankLogo: String?,
        @SerializedName("BankName")
        val bankName: String?,
        @SerializedName("BillerID")
        val billerID: String?,
        @SerializedName("City")
        val city: String?,
        @SerializedName("FooterMessage")
        val footerMessage: String?,
        @SerializedName("MerchantAddress")
        val merchantAddress: String?,
        @SerializedName("merchantCategoryCode")
        val merchantCategoryCode: String?,
        @SerializedName("MerchantID")
        val merchantID: String?,
        @SerializedName("MerchantName")
        val merchantName: String?,
        @SerializedName("PTSP")
        val ptsp: String?,
        @SerializedName("State")
        val state: String?,
        @SerializedName("TerminalID")
        val terminalID: String?,
        val baseAppVersion: String?,
        val serialNumber: String?
)

/**
 * This is a response class to card transactions made.
 *
 * @param status: This indicates the status of the transaction made from the POS. Refer to docs to find out the different codes and their meaning
 * @param transactionData: This contains the card details and more information about the transaction that has been made
 * @param message: This contains any extra message returned from the base application about the transaction.
 * */
data class CardTransactionResponse(
        val status: RequestStatus,
        val transactionData: CardTransaction?,
        val message: String? = null
)

@Parcelize
data class CardTransaction(
        val aid: String?,
        val amount: String?,
        val appLabel: String?,
        val authcode: String?,
        val bankLogo: String?,
        val bankName: String?,
        val baseAppVersion: String?,
        val cardExpireDate: String?,
        val cardHolderName: String?,
        val cashBackAmount: String?,
        val datetime: String?,
        val footerMessage: String?,
        val maskedPan: String?,
        val merchantAddress: String?,
        val merchantCategoryCode: String?,
        val merchantId: String?,
        val merchantName: String?,
        val message: String?,
        val nuban: String?,
        val pinType: String?,
        val ptsp: String?,
        val rrn: String?,
        val stan: String?,
        val statuscode: String?,
        val terminalID: String?,
        val balance: String?,
    val transactionType: String?
) : Parcelable

data class MobileMoneyTransactionResponse(
        val status: RequestStatus,
        val transactionData: MobileMoneyTransaction?
)

@Parcelize
data class MobileMoneyTransaction(
        val amount: String?,
        val dateTime: String?,
        val merchantId: String?,
        val terminalID: String?,
        val mobileNumber: String?,
        val merchantAddress: String?,
        val merchantName: String?,
        val responseCode: String?,
        val mobileOperator: String?,
        val referenceNumber: String?,
        val responseMessage: String?,
        val stan: String?
) : Parcelable

internal data class TransactionPurchaseRequest(
        val transType: String,
        val amount: String,
        val print: String
)

internal data class CardTransactionPreAuthCompletionRequest(
        val transType: String,
        val amount: String,
        val print: String,
        val reference: String
)

internal data class TransactionPurchaseWithCashBackRequest(
        val transType: String,
        val amount: String,
        val print: String,
        val cashBackAmount: String
)

internal data class MobileMoneyTransactionRequest(
        val requestType: String,
        val mobileOperator: String,
        val amount: String,
        val phoneNumber: String,
        val timeout: Int?
)

internal data class MobileMoneyRequeryRequest(
        val rrn: String,
        val stan: String,
        val transDateTime: String,
        val requestType: String = MOBILE_MONEY_STATUS_CHECK
)

internal data class CardNotPresentPurchaseRequest(
        val cardNumber: String,
        val expiryDate: String,
        val cvv: String,
        val print: String,
        val amount: String,
        var transType: String = ""
)

internal data class CardNotPresentCashBackRequest(
        val cardNumber: String,
        val cashBackAmount: String,
        val expiryDate: String,
        val cvv: String,
        val print: String,
        val amount: String,
        var transType: String = ""
)


internal data class CardNotPresentCardBalanceRequest(
        val cardNumber: String,
        val expiryDate: String,
        val cvv: String,
        val print: String,
        var transType: String = ""
)

internal data class CardNotPresentReversalRequest(
        val cardNumber: String,
        val expiryDate: String,
        val print: String,
        val cvv: String,
        val amount: String,
        val rrn: String,
        var transType: String = ""
)

internal data class CardNotPresentPreAuthCompletionRequest(
        val cardNumber: String,
        val expiryDate: String,
        val cvv: String,
        val print: String,
        val amount: String,
        val rrn: String,
        var transType: String = ""
)

sealed class RefundTransactionObject

internal data class CardReversalTransactionObject(
        val transType: String,
        val rrn: String,
        val amount: String,
        val print: String
)

internal data class CardRefundNoRRNTransactionObject(
        val transType: String,
        val rrn: String,
        val amount: String,
        val print: String
): RefundTransactionObject()

internal data class CardRefundTransactionObject(
        val transType: String,
        val amount: String,
        val print: String
): RefundTransactionObject()