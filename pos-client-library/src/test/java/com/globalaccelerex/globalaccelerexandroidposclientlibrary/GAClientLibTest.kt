package com.globalaccelerex.globalaccelerexandroidposclientlibrary

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries
import junit.framework.Assert.assertNotNull
import org.junit.Test

class GAClientLibTest{

    @Test
    fun checkCardTransaction_isInitialized_onlyWhenCalled(){

        val clientLib = GAClientLib.Builder().setCountryCode(Countries.NIGERIA).build()

        assertNotNull(clientLib.cardTransactions)
    }
}