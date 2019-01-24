package com.bunk.urbanmobility.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bunk.urbanmobility.R
import com.bunk.urbanmobility.api.GitHubDataSource
import com.bunk.urbanmobility.api.entity.RepositoryItem
import com.bunk.urbanmobility.scheduler.ObserveOnScheduler
import com.bunk.urbanmobility.scheduler.SubscribeOnScheduler
import com.bunk.urbanmobility.util.ObservableProvider
import com.bunk.urbanmobility.view.Info
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

private val TAG = DetailsViewModel::class.java.simpleName
const val REFRESH_TIME_IN_SEC = 10L // period of 10 sec,
// GitHub allows up to 10 requests per minute for unauthenticated requests

class DetailsViewModel(
    private val gitHubDataSource: GitHubDataSource,
    private val subscribeOnScheduler: SubscribeOnScheduler,
    private val observeOnScheduler: ObserveOnScheduler,
    private val observableProvider: ObservableProvider
) : ViewModel() {

    private var disposable: Disposable? = null

    val liveData = MutableLiveData<RepositoryItem>()
    val infoLiveData = MutableLiveData<Info>()

    fun fetchDetails(id: Int) {
        disposable = observableProvider.createObservableWithInterval(0, REFRESH_TIME_IN_SEC, TimeUnit.SECONDS)
            .flatMapSingle { gitHubDataSource.getDetails(id) }
            .subscribeOn(subscribeOnScheduler.io)
            .observeOn(observeOnScheduler.androidMainThreadScheduler)
            .subscribeBy(
                onNext = { liveData.value = it },
                onError = { infoLiveData.value = Info(R.string.could_not_fetch_repository_details) }
            )
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}