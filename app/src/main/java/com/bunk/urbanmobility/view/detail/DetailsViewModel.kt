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

private val TAG = DetailsViewModel::class.java.simpleName

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
        // period of 10 sec,
        // GitHub allows up to 10 requests per minute for unauthenticated requests
        disposable = observableProvider.createObservableWithInterval()
            .flatMapSingle { gitHubDataSource.getDetails(id) }
            .subscribeOn(subscribeOnScheduler.io)
            .observeOn(observeOnScheduler.androidMainThreadScheduler)
            .subscribe(
                { liveData.value = it },
                { infoLiveData.value = Info(R.string.could_not_fetch_repository_details) }
            )
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}