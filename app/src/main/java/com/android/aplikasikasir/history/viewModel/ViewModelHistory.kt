package com.android.aplikasikasir.history.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.aplikasikasir.history.model.ResponseHistory
import com.android.aplikasikasir.history.repository.RepositoryHistory
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelHistory:ViewModel() {

    private var compositeDisposable:CompositeDisposable
    init {
        compositeDisposable = CompositeDisposable()
    }

    private var repositoryHistory = RepositoryHistory(compositeDisposable)
    private var responseSuksesGetHistory = MutableLiveData<List<ResponseHistory>>()
    private var responseErrorGetHistory = MutableLiveData<Throwable>()
    private var loading = MutableLiveData<Boolean>()

    fun getHistory(){
        loading.value = true
        repositoryHistory.getHistory({
            responseSuksesGetHistory.value = it
            loading.value = false
        },{
            responseErrorGetHistory.value = it
            loading.value = false
        })
    }

    fun isSuksesHistory():LiveData<List<ResponseHistory>>{
        return responseSuksesGetHistory
    }

    fun isErrorHistory():LiveData<Throwable>{
        return responseErrorGetHistory
    }

    fun isLoading():LiveData<Boolean>{
        return loading
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}