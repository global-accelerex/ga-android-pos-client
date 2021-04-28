package com.globalaccelerex.globalaccelerexandroidposclientlibrary.util

object GaRequestKeys {
    const val PARAMETERS_REQUEST_CODE = 1001
    const val KEY_EXCHANGE_REQUEST_CODE = 1002
    const val CP_PURCHASE_REQUEST_CODE = 1003
    const val CP_PURCHASE_WITH_CASHBACK_REQUEST_CODE = 1004
    const val CP_REVERSAL_REQUEST_CODE = 1015
    const val CP_REFUND_REQUEST_CODE = 1016
    const val CP_PREAUTH_REQUEST_CODE = 1017
    const val CP_PREAUTH_COMPLETION_REQUEST_CODE = 1018
    const val MOBILE_MONEY_PURCHASE_REQUEST_CODE = 1005
    const val MOBILE_MONEY_STATUS_CHECK_REQUEST_CODE = 1006
    const val CNP_PURCHASE_REQUEST_CODE = 1007
    const val CNP_PURCHASE_WITH_CB_REQUEST_CODE = 1008
    const val CNP_PRE_AUTH_PURCHASE_REQUEST_CODE = 1009
    const val CNP_PRE_AUTH_COMPLETION_REQUEST_CODE = 1010
    const val CNP_CARD_BALANCE_REQUEST_CODE = 1011
    const val CNP_REFUND_REQUEST_CODE = 1012
    const val CNP_REVERSAL_REQUEST_CODE = 1013
    const val PRINTING_REQUEST_CODE = 1014
    const val CP_CARD_BALANCE_REQUEST_CODE = 1015
}

object TransactionStatusCodes {
    const val SUCCESS = "00"
    const val FAILED = "02"
    const val CANCEL = "03"
    const val INVALID_FORMAT = "04"
    const val WRONG_PARAMETER = "05"
    const val TIMEOUT = "06"
    const val ACTIVITY_CANCELLED = "09"
}