package com.globalaccelerex.globalaccelerexandroidposclientlibrary.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.BaseAppConstants
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.CardTransaction
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.baseAppUtils.CardTransactionResponse
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions.Transaction
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.ParsingUtil
import com.globalaccelerex.globalaccelerexandroidposclientlibrary.util.RequestStatus
import com.google.gson.Gson

/**
 * Activity Results Contracts for all possible POS APIs
 */
public object PosContracts {

    public class CardTransactionContract :
        ActivityResultContract<Transaction, CardTransactionResponse>() {
        override fun createIntent(context: Context, input: Transaction): Intent {
            return Intent(BaseAppConstants.TRANSACTION_REQUEST_INTENT_ADDRESS)
                .putExtra(BaseAppConstants.REQUEST_DATA_TAG, input.request())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): CardTransactionResponse {
            if (resultCode != Activity.RESULT_OK)
                return CardTransactionResponse(RequestStatus.CANCELLED, null)
            return try {
                val status = intent?.getStringExtra("status")
                val jsonData = intent?.getStringExtra("data")
                val cardTransaction = Gson().fromJson(jsonData, CardTransaction::class.java)
                CardTransactionResponse(
                    requestStatus = ParsingUtil.getStatus(status),
                    transactionData = cardTransaction
                )
            } catch (e: Exception) {
                CardTransactionResponse(RequestStatus.CANCELLED, null)
            }
        }
    }
}