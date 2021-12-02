package com.android.aplikasikasir.history.repository

import com.android.aplikasikasir.history.model.ResponseHistory
import com.android.aplikasikasir.network.NetworkConfig
import com.android.aplikasikasir.network.RestApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryHistory(private val compositeDisposable: CompositeDisposable) {

    var restApi = NetworkConfig().provideHttpAdapter().create(RestApi::class.java)

    fun getHistory(
        responseHandler : (List<ResponseHistory>)->Unit,
        errorHandler : (Throwable)->Unit
    ){
        compositeDisposable.add(
            restApi.listHistory()
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