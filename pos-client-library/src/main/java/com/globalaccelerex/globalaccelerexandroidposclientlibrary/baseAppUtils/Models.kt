package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants.MOBILE_MONEY_STATUS_CHECK
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.RequestStatus
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
 * @param requestStatus: This indicates the status of the transaction made from the POS. Refer to docs to find out the different codes and their meaning
 * @param transactionData: This contains the card details and more information about the transaction that has been made
 * */
data class CardTransactionResponse(
    val requestStatus: RequestStatus?,
    val transactionData: CardTransaction?
)

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
    val transactionType: String?
)

data class MobileMoneyTransactionResponse(
    val status: RequestStatus,
    val transactionData: MobileMoneyTransaction
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