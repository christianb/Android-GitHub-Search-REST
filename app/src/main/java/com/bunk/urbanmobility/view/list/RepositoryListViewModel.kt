package com.bunk.urbanmobility.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bunk.urbanmobility.R
import com.bunk.urbanmobility.api.GitHubDataSource
import com.bunk.urbanmobility.api.entity.RepositoryItem
import com.bunk.urbanmobility.scheduler.ObserveOnScheduler
import com.bunk.urbanmobility.scheduler.SubscribeOnScheduler
import com.bunk.urbanmobility.view.Info
import com.bunk.urbanmobility.view.ShowProgressBar
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

const val DEFAULT_STARS = 10000

class RepositoryListViewModel(
    private val gitHubDataSource: GitHubDataSource,
    private val subscribeOnScheduler: SubscribeOnScheduler,
    private val observeOnScheduler: ObserveOnScheduler
) : ViewModel() {

    private var disposable: Disposable? = null

    val liveData = MutableLiveData<List<RepositoryItem>>()
    val infoLiveData = MutableLiveData<Info>()
    val progressBarLiveData = MutableLiveData<ShowProgressBar>()

    fun fetchRepositories() {
        progressBarLiveData.value = ShowProgressBar(true)
        disposable = gitHubDataSource.getRepositories(DEFAULT_STARS)
            .subscribeOn(subscribeOnScheduler.io)
            .observeOn(observeOnScheduler.androidMainThreadScheduler)
            .subscribeBy(
                onSuccess = {
                    val list = liveData.value ?: emptyList()
                    val mutableList = mutableListOf<RepositoryItem>()

                    mutableList.addAll(list)
                    mutableList.addAll(it)

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

        disposable?.dispose()
    }
}