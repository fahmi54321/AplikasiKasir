package com.android.aplikasikasir.details.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.aplikasikasir.details.model.ResponseDetails
import com.android.aplikasikasir.details.repository.RepositoryDetails
import com.android.aplikasikasir.history.repository.RepositoryHistory
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelDetails:ViewModel() {

    private var compositeDisposable:CompositeDisposable
    init {
        compositeDisposable = CompositeDisposable()
    }

    private var repositoryDetails = RepositoryDetails(compositeDisposable)
    private var responseSuksesDetails = MutableLiveData<List<ResponseDetails>>()
    private var responseErrorDetails = MutableLiveData<Throwable>()
    private var idKosong = MutableLiveData<Boolean>()
    private var loading = MutableLiveData<Boolean>()

    fun getDetails(
        id:String
    ){
        loading.value = true
        if (id.isNullOrEmpty()){
            idKosong.value = true
            loading.value = false
        }else{
            repositoryDetails.getDetails(id,{
                responseSuksesDetails.value = it
                loading.value = false
            },{
                responseErrorDetails.value = it
                loading.value = false
            })
        }
    }

    fun isSuksesDetails():LiveData<List<ResponseDetails>>{
        return responseSuksesDetails
    }

    fun isErrorDetails():LiveData<Throwable>{
        return responseErrorDetails
    }

    fun idIsEmpty():LiveData<Boolean>{
        return idKosong
    }

    fun isLoading():LiveData<Boolean>{
        return loading
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}