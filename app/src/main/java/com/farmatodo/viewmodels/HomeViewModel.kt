package com.farmatodo.viewmodels

import androidx.lifecycle.MutableLiveData
import com.farmatodo.R
import com.farmatodo.application.App
import com.farmatodo.data.*
import com.farmatodo.models.HomeModel

class HomeViewModel : BaseViewModel() {

    private val model = HomeModel()
    private val appContext = App.appComponent.appContext()
    val onDataLoaded: MutableLiveData<MutableList<BaseData>> by lazy {
        MutableLiveData<MutableList<BaseData>>()
    }
    val onDataDetailLoaded: MutableLiveData<BaseData?> by lazy {
        MutableLiveData<BaseData?>()
    }
    val onTitleChanged: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun makeRequest(exp : String){
        showLoading()
        disposables.add(model.getRequestObservable(exp)
            .subscribe({
                hideLoading()
                when (it) {
                    is CharacterResult -> {
                        onDataLoaded.value = it.data?.results?.toMutableList()
                        onTitleChanged.value = appContext.getString(R.string.characters)
                    }
                    is ComicResult -> {
                        onDataLoaded.value = it.data?.results?.toMutableList()
                        onTitleChanged.value = appContext.getString(R.string.comics)
                    }
                    is EventResult -> {
                        onDataLoaded.value = it.data?.results?.toMutableList()
                        onTitleChanged.value = appContext.getString(R.string.events)
                    }
                    is SerieResult -> {
                        onDataLoaded.value = it.data?.results?.toMutableList()
                        onTitleChanged.value = appContext.getString(R.string.series)
                    }
                    is StoryResult -> {
                        onDataLoaded.value = it.data?.results?.toMutableList()
                        onTitleChanged.value = appContext.getString(R.string.stories)
                    }
                    is CreatorResult -> {
                        onDataLoaded.value = it.data?.results?.toMutableList()
                        onTitleChanged.value = appContext.getString(R.string.creators)
                    }
                }
            }, {
                hideLoading()
                onError.value = appContext.getString(R.string.error)
            }))
    }

    fun getDetail(data : BaseData){
        showLoading()
        disposables.add(model.getDetailRequestObservable(data)
            .map {
                return@map when (it) {
                    is CharacterResult -> {
                        it.data?.results?.first() as BaseData
                    }
                    is ComicResult -> {
                        it.data?.results?.first() as BaseData
                    }
                    is EventResult -> {
                        it.data?.results?.first() as BaseData
                    }
                    is SerieResult -> {
                        it.data?.results?.first() as BaseData
                    }
                    is StoryResult -> {
                        it.data?.results?.first() as BaseData
                    }
                    is CreatorResult -> {
                        it.data?.results?.first() as BaseData
                    }
                    else -> null
                }
            }
            .subscribe({
                hideLoading()
                onDataDetailLoaded.value = it

            }, {
                hideLoading()
                onError.value = appContext.getString(R.string.error)
            }))
    }

    fun clearDetail(){
        onDataDetailLoaded.value = null
    }


}
