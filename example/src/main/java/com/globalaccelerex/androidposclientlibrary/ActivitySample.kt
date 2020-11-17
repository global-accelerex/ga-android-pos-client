package com.globalaccelerex.androidposclientlibrary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.GAClientLib
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.FontSize
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.ReceiptFormat
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing.TextAlignment
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.Countries
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.MobileMoneyOperators
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.Exception

/**
 * This activity shows an example of how each library function is used.
 * Same principles apply when calling from a fragment
 * */

class ActivitySample : AppCompatActivity() {

    //By default the library is initialized with Nigerian configuration.
    private var clientLib: GAClientLib = GAClientLib.Builder()
        .setCountryCode(Countries.NIGERIA)
        .build()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pos_config_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nigeria_config -> {
                clientLib = GAClientLib.Builder()
                    .setCountryCode(Countries.NIGERIA)
                    .build()
                Toast.makeText(this, "Currently operating with Nigeria configuration", Toast.LENGTH_SHORT).show()
            }
            R.id.kenya_config -> {
                clientLib = GAClientLib.Builder()
                    .setCountryCode(Countries.KENYA)
                    .build()
                Toast.makeText(this, "Currently operating with Kenya configuration", Toast.LENGTH_SHORT).show()
            }
            R.id.ghana_config -> {
                clientLib = GAClientLib.Builder()
                    .setCountryCode(Countries.GHANA)
                    .build()
                Toast.makeText(this, "Currently operating with Ghana configuration", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make Key Exchange
        try {
            key_exchange.setOnClickListener {
                clientLib.makeKeyExchangeRequest(this)
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        //Make parameter request
        try {
            paramter_request.setOnClickListener {
                clientLib.makeParametersRequest(this)
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        //Make Card Present Transactions (CP)
        try {
            transaction_request.setOnClickListener {
                clientLib.cardTransactions.purchase(
                    amount = 0.01,
                    callingComponent = this,
                    customPrint = true
                )
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        try {
            mobile_money_request.setOnClickListener {
                clientLib.mobileMoneyTransaction.purchase(
                    phoneNumber = "08143051805",
                    amount = 1.0,
                    mobileOperator = MobileMoneyOperators.MTN,
                    callingComponent = this
                )
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        try {
            cnp_purchase.setOnClickListener {
                clientLib.cardNotPresentTransactions.purchase(
                    cardNumber = "5196010125746208",
                    cardExpiryDate = "2302",
                    callingComponent = this,
                    amount = 1.0,
                    customPrint = false
                )
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        print_receipt.setOnClickListener {
            val receiptFormat = ReceiptFormat(applicationContext)
            receiptFormat.addSingleLine("This is a default line")
            receiptFormat.addSingleLine("This is a bold line", isBold = true)
            receiptFormat.addSingleLine("This is a large text", isBold = true, fontSize = FontSize.LARGE)
            receiptFormat.addSingleLine("This is the first printing line also testing multiline so this text has to be very very long", TextAlignment.ALIGN_LEFT)
            receiptFormat.addSpaceDivider()
            receiptFormat.addSingleLine("Just to confirm!", TextAlignment.ALIGN_RIGHT)
            receiptFormat.addLineDivider()
            receiptFormat.addKeyValuePair(key = "Header", value = "this value", isMultiLine = false, isBold = true, fontSize = FontSize.LARGE)
            clientLib.printer.printReceipt(receipt = receiptFormat.generatePaymentReceipt(), callingComponent = this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Note that the Request key will always match the transaction type with "REQUEST_CODE" appended to it.

        if (resultCode == Activity.RESULT_OK) {

        }
    }

}