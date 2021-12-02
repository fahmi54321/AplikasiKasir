package com.android.aplikasikasir.preview.repository

import com.android.aplikasikasir.network.NetworkConfig
import com.android.aplikasikasir.network.RestApi
import com.android.aplikasikasir.preview.model.ResponseSave
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositorySave(private var compositeDisposable: CompositeDisposable) {

    var restApi = NetworkConfig().provideHttpAdapter().create(RestApi::class.java)

    fun save(
        idProduk:String,
        qty:String,
        responHandler : (ResponseSave)->Unit,
        errorHandler : (Throwable)->Unit
    ){
        compositeDisposable.add(
            restApi.save(idProduk,qty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    responHandler(it)
                },{
                    errorHandler(it)
                })
        )
    }

}