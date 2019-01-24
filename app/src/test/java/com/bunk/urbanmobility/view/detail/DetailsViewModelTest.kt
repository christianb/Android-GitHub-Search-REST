package com.bunk.urbanmobility.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bunk.urbanmobility.R
import com.bunk.urbanmobility.TestSchedulerProvider
import com.bunk.urbanmobility.api.GitHubDataSource
import com.bunk.urbanmobility.api.entity.RepositoryItem
import com.bunk.urbanmobility.util.ObservableProvider
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class DetailsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val gitHubDataSource: GitHubDataSource = mock()
    private val observableProvider: ObservableProvider = mock()

    lateinit var classToTest: DetailsViewModel

    @Before
    fun setUp() {
        val testSchedulerProvider = TestSchedulerProvider()
        classToTest = DetailsViewModel(
            gitHubDataSource,
            testSchedulerProvider.subscribeOnScheduler,
            testSchedulerProvider.observeOnScheduler,
            observableProvider
        )
    }

    @Test
    fun `fetchDetails should update live data when successful`() {
        val id = 42
        val repositoryItem = mock<RepositoryItem>()
        whenever(observableProvider.createObservableWithInterval(0, REFRESH_TIME_IN_SEC, TimeUnit.SECONDS)).thenReturn(Observable.just(1))
        whenever(gitHubDataSource.getDetails(id)).thenReturn(Single.just(repositoryItem))

        classToTest.fetchDetails(id)

        assertThat(classToTest.liveData.value).isEqualTo(repositoryItem)
    }

    @Test
    fun `fetchDetails should show info when failure`() {
        val id = 42
        whenever(observableProvider.createObservableWithInterval(0, REFRESH_TIME_IN_SEC, TimeUnit.SECONDS)).thenReturn(Observable.just(1))
        whenever(gitHubDataSource.getDetails(id)).thenReturn(Single.error(Exception()))

        classToTest.fetchDetails(id)

        assertThat(classToTest.infoLiveData.value!!.resId).isEqualTo(R.string.could_not_fetch_repository_details)
    }
}