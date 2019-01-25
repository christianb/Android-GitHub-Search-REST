package com.bunk.urbanmobility.view.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bunk.urbanmobility.R
import com.bunk.urbanmobility.data.entity.RepositoryItem
import com.bunk.urbanmobility.domain.GitHubRepository
import com.bunk.urbanmobility.scheduler.ObserveOnScheduler
import com.bunk.urbanmobility.scheduler.SubscribeOnScheduler
import com.bunk.urbanmobility.view.Info
import com.bunk.urbanmobility.view.ShowProgressBar
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

const val DEFAULT_STARS = 10000
private val TAG = RepositoryListViewModel::class.java.simpleName

class RepositoryListViewModel(
    private val gitHubRepository: GitHubRepository,
    private val subscribeOnScheduler: SubscribeOnScheduler,
    private val observeOnScheduler: ObserveOnScheduler
) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    val liveData = MutableLiveData<List<RepositoryItem>>()
    val infoLiveData = MutableLiveData<Info>()
    val progressBarLiveData = MutableLiveData<ShowProgressBar>()

    fun loadNextPage() {
        Log.d(TAG, "load next page")
        progressBarLiveData.value = ShowProgressBar(true)
        gitHubRepository.getNextPage(DEFAULT_STARS).doSubscribe().addTo(compositeDisposable)
    }

    fun fetchRepositories() {
        progressBarLiveData.value = ShowProgressBar(true)
        gitHubRepository.getRepositories(DEFAULT_STARS).doSubscribe().addTo(compositeDisposable)
    }

    private fun Single<List<RepositoryItem>>.doSubscribe(): Disposable {
        return subscribeOn(subscribeOnScheduler.io)
            .observeOn(observeOnScheduler.androidMainThreadScheduler)
            .subscribeBy(
                onSuccess = {
                    val list = liveData.value ?: emptyList()
                    val mutableList = mutableListOf<RepositoryItem>()

                    mutableList.addAll(list)
                    mutableList.addAll(oit)

                    liveData.value = mutableList
                    progressBarLiveData.value = ShowProgressBar(false)
                },

                onError = {
                    infoLiveData.value = Info(R.string.could_not_fetch_repositories)
                    progressBarLiveData.value = ShowProgressBar(false)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}