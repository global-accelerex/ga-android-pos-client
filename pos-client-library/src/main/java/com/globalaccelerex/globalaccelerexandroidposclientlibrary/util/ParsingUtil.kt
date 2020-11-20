package com.globalaccelerex.globalaccelerexandroidposclientlibrary.util

internal object ParsingUtil {

    fun getStatus(code: String?): RequestStatus {
        return when(code) {
            "00" -> RequestStatus.SUCCESS
            "02" -> RequestStatus.FAILED
            "03" -> RequestStatus.CANCELLED
            "06" -> RequestStatus.TIMEOUT
            else -> RequestStatus.FAILED
        }
    }
}