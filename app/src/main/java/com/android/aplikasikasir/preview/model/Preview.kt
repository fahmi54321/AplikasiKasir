package com.android.aplikasikasir.preview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Preview(
    val idProduk:String,
    val namaProduk:String,
    val harga:String,
    val jumlah:String,
):Parcelable