package com.globalaccelerex.globalaccelerexandroidposclientlibrary.printing

import com.google.gson.annotations.SerializedName

data class Receipt(@SerializedName( "Receipt") val printFields: List<PrintField>)

data class PrintField(
    @SerializedName( "Bitmap") val filename: String = "",
    @SerializedName( "letterSpacing") val letterSpacing: Int = 5,
    @SerializedName( "String") val stringFields: List<StringField> = ArrayList()
)

data class StringField(
    @SerializedName( "isMultiline") val isMultiline: Boolean,
    @SerializedName( "header") val header: TextField = TextField(),
    @SerializedName( "body") val body: TextField = TextField()
)

data class TextField(
    @SerializedName("text") val text: String = "",
    @SerializedName( "align") val align: String? = null,
    @SerializedName( "size") val size: String? = null,
    @SerializedName( "isBold") val isBold: Boolean? = null
)