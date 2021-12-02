package com.android.aplikasikasir.preview.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.aplikasikasir.preview.model.ResponseSave
import com.android.aplikasikasir.preview.repository.RepositorySave
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelSave: ViewModel() {

    private var compositeDisposable: CompositeDisposable
    init {
        compositeDisposable = CompositeDisposable()
    }

    private var repositorySave=RepositorySave(compositeDisposable)
    private var responseSuksesSave = MutableLiveData<ResponseSave>()
    private var responseErrorSave = MutableLiveData<Throwable>()
    private var loading = MutableLiveData<Boolean>()
    private var idProdukKosong = MutableLiveData<Boolean>()
    private var qtyKosong = MutableLiveData<Boolean>()

    fun save(
        idProduk:String,
        qty:String
    ){
        loading.value = true
        if (idProduk.isEmpty()){
            idProdukKosong.value = true
            loading.value = false
        }else if (qty.isEmpty()){
            qtyKosong.value = true
            loading.value = false
        }else{
            repositorySave.save(idProduk,qty,{
                responseSuksesSave.value = it
                loading.value = false
            },{
                responseErrorSave.value = it
                loading.value = false
            })
        }
    }

    fun isSuksesSave():LiveData<ResponseSave>{
        return responseSuksesSave
    }
    fun isErrorSave():LiveData<Throwable>{
        return responseErrorSave
    }
    fun idProdukIsEmpty():LiveData<Boolean>{
        return idProdukKosong
    }
    fun qtyIsEmpty():LiveData<Boolean>{
        return qtyKosong
    }
    fun isLoading():LiveData<Boolean>{
        return loading
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}