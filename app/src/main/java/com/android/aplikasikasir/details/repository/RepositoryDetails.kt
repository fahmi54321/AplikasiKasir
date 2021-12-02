package com.android.aplikasikasir.details.repository

import com.android.aplikasikasir.details.model.ResponseDetails
import com.android.aplikasikasir.network.NetworkConfig
import com.android.aplikasikasir.network.RestApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryDetails(private val compositeDisposable: CompositeDisposable) {

    var restApi = NetworkConfig().provideHttpAdapter().create(RestApi::class.java)

    fun getDetails(
        id:String,
        responseHandler: (List<ResponseDetails>) -> Unit,
        errorHandler: (Throwable) -> Unit
    ){
        compositeDisposable.add(
            restApi.detailsHistory(id)
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