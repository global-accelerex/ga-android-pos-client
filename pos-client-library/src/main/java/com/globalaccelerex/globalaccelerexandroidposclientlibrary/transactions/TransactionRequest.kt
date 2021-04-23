package com.globalaccelerex.globalaccelerexandroidposclientlibrary.transactions

import com.google.gson.annotations.SerializedName

internal data class TransactionRequest(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("cashBackAmount")
    val cashBackAmount: String? = null,
    @SerializedName("transType")
    val transType: String,
    @SerializedName("print")
    val printReceipt: String,
    /**
     * Custom reference
     */
    @SerializedName("transactionReference")
    val reference: String? = null,

    /**
     * For things like reversals, sale completion
     */
    @SerializedName("rrn")
    val rrn: String? = null
)
