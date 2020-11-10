package com.globalaccelerex.globalaccelerexandroidposclientlibrary

import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedFeatureException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class GAClientLibTest{

    lateinit var clientLib: GAClientLib

    @get: Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun checkCardTransaction_isInitialized_onlyWhenCalled(){

        clientLib = GAClientLib.Builder().setCountryCode(Countries.NIGERIA).build()

        assertNotNull(clientLib.cardTransactions)
    }

    @Test
    fun checkMobileMoneyTransaction_isOnlyAvailable_withGhanaConfig() {

        //Should work fine with Ghana config
        clientLib = GAClientLib.Builder().setCountryCode(Countries.GHANA).build()
        assertNotNull(clientLib.mobileMoneyTransaction)
        println("Mobile money Works with Ghana config")

        //Should throw exception with kenya configuration
        clientLib = GAClientLib.Builder().setCountryCode(Countries.KENYA).build()
        expectedException.expect(UnsupportedFeatureException::class.java)
        println("Mobile money Threw correct exception for Kenya config")
        clientLib.mobileMoneyTransaction
    }

    @Test(expected = UnsupportedFeatureException::class)
    fun checkMobileMoneyTransaction_throwsException_withNigeriaConfig() {

        //Should throw exception with Nigeria configuration
        clientLib = GAClientLib.Builder().setCountryCode(Countries.NIGERIA).build()
        println("Mobile Money Threw correct exception for Nigeria config")
        clientLib.mobileMoneyTransaction

    }

    @Test
    fun checkCardNotPresentTransaction_isNotAvailable_inNigeria() {

        clientLib = GAClientLib.Builder().setCountryCode(Countries.NIGERIA).build()
        expectedException.expect(UnsupportedFeatureException::class.java)
        println("Throw correct exception for CNP attempt with Nigeria config.")
        clientLib.cardNotPresentTransactions

    }


}


























