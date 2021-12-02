package com.android.aplikasikasir.produk.respository

import com.android.aplikasikasir.network.NetworkConfig
import com.android.aplikasikasir.network.RestApi
import com.android.aplikasikasir.produk.model.ResponseProduk
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryListProduk(
    private var compositeDisposable: CompositeDisposable
) {
    private var apiService = NetworkConfig().provideHttpAdapter().create(RestApi::class.java)

    fun getProduk(
        responseHandler : (List<ResponseProduk>)->Unit,
        errorHandler : (Throwable) -> Unit

    ){
        compositeDisposable.add(
            apiService.listProduk()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    responseHandler(it)
                },{
                    errorHandler(it)
                })
        )
    }

}