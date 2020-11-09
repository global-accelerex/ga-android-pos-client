package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_STATUS_CHECK
import com.google.gson.annotations.SerializedName

data class PosInformation(
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("Location")
    val location: String?,
    @SerializedName("FooterMessage")
    val footerMessage: String?,
    @SerializedName("MerchantID")
    val merchantID: String?,
    @SerializedName("MerchantName")
    val merchantName: String?,
    @SerializedName("PTSP")
    val pTSP: String?,
    @SerializedName("serialNumber")
    val serialNumber: String?,
    @SerializedName("Biller_ID")
    val billerId: String?,
    @SerializedName("TerminalID")
    val terminalId: String?,
    @SerializedName("baseAppVersion")
    val baseAppVersion: String?
)

/**
 * This is a response class to card transactions made.
 *
 * @param status: This indicates the status of the transaction made. Refer to docs to find out the different codes and their meaning
 * @param transactionData: This contains the card details and more information about the transaction that has been made
 * */
data class CardTransactionResponse(
    val status: String?,
    val transactionData: CardTransaction?
)

data class CardTransaction(
    @SerializedName("statuscode") val statuscode: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("terminalID") val terminalID: String?,
    @SerializedName("rrn") val rrn: String?,
    @SerializedName("stan") val stan: String?,
    @SerializedName("amount") val amount: String?,
    @SerializedName("datetime") var datetime: String?,
    @SerializedName("authcode") val authcode: String? = "",
    @SerializedName("cardHolderName") val cardHolderName: String?,
    @SerializedName("maskedPan") val maskedPan: String?,
    @SerializedName("appLabel") val cardScheme: String?,
    @SerializedName("cardExpireDate") val cardExpireDate: String?,
    @SerializedName("aid") val aid: String?,
    @SerializedName("nuban") val nuban: String? = "",
    @SerializedName("pinType") val pinType: String? = "",
    @SerializedName("statusDescription") val statusDescription: String? = ""
)

data class MobileMoneyTransactionResponse(
    val status: String?,
    val transactionData: MobileMoneyTransaction?
)

data class MobileMoneyTransaction(
    val amount: String?,
    val dateTime: String?,
    val mobileNumber: String?,
    val responseCode: String?,
    val mobileOperator: String?,
    val referenceNumber: String?,
    val responseMessage: String?,
    val stan: String?
)

internal data class TransactionPurchaseRequest(
    val transType: String,
    val amount: String,
    val print: String
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
    val phoneNumber: String
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
    val print: String,
    val amount: String,
    var transType: String = ""
)

internal data class CardNotPresentCashBackRequest(
    val cardNumber: String,
    val cashBackAmount: String,
    val expiryDate: String,
    val print: String,
    val amount: String,
    var transType: String = ""
)


internal data class CardNotPresentCardBalanceRequest(
    val cardNumber: String,
    val expiryDate: String,
    val print: String,
    var transType: String = ""
)

internal data class CardNotPresentReversalRequest(
    val cardNumber: String,
    val expiryDate: String,
    val print: String,
    val amount: String,
    val rrn: String,
    var transType: String = ""
)

internal data class CardNotPresentPreAuthCompletionRequest(
    val cardNumber: String,
    val expiryDate: String,
    val print: String,
    val amount: String,
    val rrn: String,
    var transType: String = ""
)