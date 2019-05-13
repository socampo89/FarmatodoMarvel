package com.farmatodo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    val onLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val onError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun showLoading(){
        onLoading.value = true;
    }

    fun hideLoading(){
        onLoading.value = false
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}