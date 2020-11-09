package com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions

import java.lang.Exception

class UnsupportedFeatureException(private val customMessage: String): Exception() {
    override val message: String?
        get() = this.customMessage
}

class UnsupportedCallingComponentException(private val customMessage: String): Exception() {
    override val message: String?
        get() = this.customMessage
}