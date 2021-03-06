package com.globalaccelerex.globalaccelerexandroidposclientlibrary.util

enum class Countries{
    NIGERIA, KENYA, GHANA
}

enum class MobileMoneyOperators{
    MTN, TIGO, VODAFONE
}

sealed class RequestStatus {
    object SUCCESS: RequestStatus()
    object FAILED: RequestStatus()
    object CANCELLED: RequestStatus()
    object TIMEOUT: RequestStatus()
}