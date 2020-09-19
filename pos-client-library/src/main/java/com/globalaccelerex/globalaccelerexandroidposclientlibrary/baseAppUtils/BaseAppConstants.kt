package com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils

internal object BaseAppConstants {

    const val PARAMETERS_REQUEST_INTENT_ADDRESS = "com.globalaccelerex.utility"
    const val KEY_EXCHANGE_REQUEST_INTENT_ADDRESS = "com.globalaccelerex.keyexchange"
    const val TRANSACTION_REQUEST_INTENT_ADDRESS = "com.globalaccelerex.transaction"
    const val MOBILE_MONEY_TRANSACTION_INTENT_ADDRESS = "com.globalaccelerex.gh_mobile_money"
    const val CARD_NOT_PRESENT_TRANSACTION_INTENT = "com.globalaccelerex.card_not_present"
    const val PRINTER_INTENT_ADDRESS = "com.globalaccelerex.printer"


    const val REQUEST_DATA_TAG = "requestData"
    const val MOBILE_MONEY_REQUEST_DATA_TAG = "mobileMoneyRequest"
    const val PARAMETERS_REQUEST_FORMAT = "{  \"action\":\"PARAMETER\"  }"

    const val TRANSACTION_TYPE_PURCHASE = "PURCHASE"
    const val TRANSACTION_TYPE_PURCHASE_WITH_CASH_BACK = "PURCHASEWITHCB"
    const val TRANSACTION_TYPE_PRE_AUTH_PURCHASE = "PRE_AUTH"
    const val TRANSACTION_TYPE_PRE_AUTH_PURCHASE_COMPLETION = "PRE_AUTH_COMPLETION"
    const val TRANSACTION_TYPE_REVERSAL = "REVERSAL"
    const val TRANSACTION_TYPE_REFUND = "REFUND"
    const val TRANSACTION_TYPE_CARD_BALANCE = "BALANCE"
    const val MOBILE_MONEY_TRANSACTION = "TRANSACTION"
    const val MOBILE_MONEY_STATUS_CHECK = "RE_QUERY"

    const val SUCCESS = "00"
    const val FAILURE = "02"
}