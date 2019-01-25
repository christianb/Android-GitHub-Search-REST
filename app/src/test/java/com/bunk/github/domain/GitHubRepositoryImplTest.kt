package com.bunk.github.domain

import com.bunk.github.data.GitHubDataSource
import com.bunk.github.data.entity.RepositoryItem
import com.bunk.github.data.entity.RepositoryResponse
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class GitHubRepositoryImplTest {

    private val gitHubDataSource: GitHubDataSource = mock()

    private lateinit var classToTest: GitHubRepositoryImpl

    @Before
    fun setUp() {
        classToTest = GitHubRepositoryImpl(gitHubDataSource)
    }

    @Test
    fun getNextPage() {
        val repositoryResponse = mock<RepositoryResponse>()
        val repositoryItem: RepositoryItem = mock()
        val list: List<RepositoryItem> = listOf(repositoryItem)
        whenever(repositoryResponse.items).thenReturn(list)
        whenever(gitHubDataSource.getRepositories(eq(50), any())).thenReturn(Single.just(repositoryResponse))

        var result: List<RepositoryItem>? = null

        classToTest.getNextRepositoryPage(50)
            .subscribeBy(
                onSuccess = { result = it }
            )

        assertThat(result).containsExactly(repositoryItem)
    }

    @Test
    fun getRepositories() {
        val repositoryResponse = mock<RepositoryResponse>()
        val repositoryItem: RepositoryItem = mock()
        val list: List<RepositoryItem> = listOf(repositoryItem)
        whenever(repositoryResponse.items).thenReturn(list)
        whenever(gitHubDataSource.getRepositories(eq(50), any())).thenReturn(Single.just(repositoryResponse))

        var result: List<RepositoryItem>? = null

        classToTest.getRepositories(50)
            .subscribeBy(
                onSuccess = { result = it }
            )

        assertThat(result).containsExactly(repositoryItem)
    }

    @Test
    fun getDetails() {
        val id = 42
        val repositoryItem: RepositoryItem = mock()
        whenever(gitHubDataSource.getDetails(id)).thenReturn(Single.just(repositoryItem))

        var result: RepositoryItem? = null

        classToTest.getDetails(id)
            .subscribeBy(
                onSuccess = { result = it }
            )

        assertThat(result).isEqualTo(repositoryItem)
    }
}