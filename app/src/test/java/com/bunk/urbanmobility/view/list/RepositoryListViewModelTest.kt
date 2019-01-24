package com.bunk.urbanmobility.view.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bunk.urbanmobility.R
import com.bunk.urbanmobility.TestSchedulerProvider
import com.bunk.urbanmobility.api.GitHubDataSource
import com.bunk.urbanmobility.api.entity.RepositoryItem
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepositoryListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val gitHubDataSource: GitHubDataSource = mock()

    private lateinit var classToTest: RepositoryListViewModel

    @Before
    fun setUp() {
        val testSchedulerProvider = TestSchedulerProvider()
        classToTest = RepositoryListViewModel(
            gitHubDataSource,
            testSchedulerProvider.subscribeOnScheduler,
            testSchedulerProvider.observeOnScheduler
        )
    }

    @Test
    fun `fetchRepositories should set repository list when successful`() {
        val repositoryItem = mock<RepositoryItem>()
        val list: List<RepositoryItem> = listOf(repositoryItem)
        whenever(gitHubDataSource.getRepositories(DEFAULT_STARS)).thenReturn(Single.just(list))

        classToTest.fetchRepositories()

        assertThat(classToTest.liveData.value).containsExactly(repositoryItem)
    }

    @Test
    fun `fetchRepositories should set info when failure`() {
        whenever(gitHubDataSource.getRepositories(DEFAULT_STARS)).thenReturn(Single.error(Exception()))

        classToTest.fetchRepositories()

        assertThat(classToTest.infoLiveData.value!!.resId).isEqualTo(R.string.could_not_fetch_repositories)
    }
}