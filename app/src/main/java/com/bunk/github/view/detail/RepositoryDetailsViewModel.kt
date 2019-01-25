package com.bunk.github.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bunk.github.R
import com.bunk.github.data.entity.RepositoryItem
import com.bunk.github.domain.GitHubRepository
import com.bunk.github.scheduler.ObserveOnScheduler
import com.bunk.github.scheduler.SubscribeOnScheduler
import com.bunk.github.util.ObservableProvider
import com.bunk.github.view.Info
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

private val TAG = DetailsViewModel::class.java.simpleName
const val REFRESH_TIME_IN_SEC = 10L // period of 10 sec,
// GitHub allows up to 10 requests per minute for unauthenticated requests

class DetailsViewModel(
    private val gitHubRepository: GitHubRepository,
    private val subscribeOnScheduler: SubscribeOnScheduler,
    private val observeOnScheduler: ObserveOnScheduler,
    private val observableProvider: ObservableProvider
) : ViewModel() {

    private var disposable: Disposable? = null

    val liveData = MutableLiveData<RepositoryItem>()
    val infoLiveData = MutableLiveData<Info>()

    fun fetchDetails(id: Int) {
        disposable = observableProvider.createObservableWithInterval(0, REFRESH_TIME_IN_SEC, TimeUnit.SECONDS)
            .flatMapSingle { gitHubRepository.getDetails(id) }
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