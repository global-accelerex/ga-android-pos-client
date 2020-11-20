package com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions

import android.app.Activity
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.exceptions.UnsupportedFeatureException
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries
import org.junit.Test

import org.junit.Assert.*

class CardNotPresentTransactionsTest {

    private val cnpTrans = CardNotPresentTransactions(Countries.GHANA)

    @Test(expected = IllegalArgumentException::class)
    fun test_Purchase_FakeCardNumberValidation_Throws_Exception() {
        cnpTrans.purchase(amount = 1.0, cardNumber = "1234", cardExpiryDate = "2345", callingComponent = Activity::class, customPrint = false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_purchase_wrongCardExpiryValidation_throws_exception() {
        cnpTrans.purchase(amount = 1.0, cardNumber = "12345555555555", cardExpiryDate = "245", callingComponent = Activity::class, customPrint = false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_cashBackPurchase_cashBackGreaterThanAmount_throwsException() {
        cnpTrans.purchaseWithCashBack(amount = 1.0, cashBackAmount = 2.0 ,cardNumber = "12345555555555", cardExpiryDate = "2453", callingComponent = Activity::class, customPrint = false)
    }


}