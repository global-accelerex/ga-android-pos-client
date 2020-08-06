package com.globalaccelerex.globalaccelerexandroidposclientlibrary.util

import junit.framework.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun testDoubleAmountConversionToValidStringFormat(){
        val testDouble = 2.0
        val testDoubleSecond = 2.1234
        assertEquals("2.00", testDouble.toPosAmount())
        assertEquals("2.12", testDoubleSecond.toPosAmount())
    }
}