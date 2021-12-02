package com.android.aplikasikasir.network

import com.android.aplikasikasir.details.model.ResponseDetails
import com.android.aplikasikasir.history.model.ResponseHistory
import com.android.aplikasikasir.preview.model.ResponseSave
import com.android.aplikasikasir.produk.model.ResponseProduk
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestApi {

    @GET("produk")
    fun listProduk():Flowable<List<ResponseProduk>>

    @Headers("x-key: bd0b3ae6651538fac2515baafc9326c5")
    @FormUrlEncoded
    @POST("penjualan/save")
    fun save(
        @Field("id_produk[]") idProduk:String,
        @Field("qty[]") qty:String,
    ):Single<ResponseSave>

    @Headers("x-key: bd0b3ae6651538fac2515baafc9326c5")
    @GET("penjualan")
    fun listHistory():Flowable<List<ResponseHistory>>

    @Headers("x-key: bd0b3ae6651538fac2515baafc9326c5")
    @GET("penjualan/detail")
    fun detailsHistory(
        @Query("id") id:String
    ):Flowable<List<ResponseDetails>>

}