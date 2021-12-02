package com.android.aplikasikasir.produk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.Field

@Parcelize
data class ResponseProduk(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("harga")
    var harga: String? = null
):Parcelable