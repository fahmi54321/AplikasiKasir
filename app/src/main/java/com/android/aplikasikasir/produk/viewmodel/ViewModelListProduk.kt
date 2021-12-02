package com.android.aplikasikasir.produk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.aplikasikasir.produk.model.ResponseProduk
import com.android.aplikasikasir.produk.respository.RepositoryListProduk
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelListProduk():ViewModel() {

    private var compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    private var repositoryListProduk = RepositoryListProduk(compositeDisposable)
    private var responseSuksesGetProduk = MutableLiveData<List<ResponseProduk>>()
    private var responseErrorGetProduk = MutableLiveData<Throwable>()
    private var loading = MutableLiveData<Boolean>()

    fun getProduk(){
        loading.value = true
        repositoryListProduk.getProduk({
            responseSuksesGetProduk.value = it
            loading.value = false
        },{
            responseErrorGetProduk.value = it
            loading.value = false
        })
    }

    fun isSuksesGetProduk():LiveData<List<ResponseProduk>>{
        return responseSuksesGetProduk
    }

    fun isErrorGetProduk():LiveData<Throwable>{
        return responseErrorGetProduk
    }

    fun isLoading():LiveData<Boolean>{
        return loading
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}