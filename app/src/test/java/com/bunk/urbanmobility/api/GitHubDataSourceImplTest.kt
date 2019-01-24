package com.bunk.urbanmobility.api

import com.bunk.urbanmobility.api.entity.RepositoryItem
import com.bunk.urbanmobility.api.entity.RepositoryResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class GitHubDataSourceImplTest {

    private val gitHubApi: GitHubApi = mock()

    lateinit var classToTest: GitHubDataSourceImpl

    @Before
    fun setUp() {
        classToTest = GitHubDataSourceImpl(gitHubApi)
    }

    @Test
    fun `getRepositories should return list`() {
        val stars = 50
        val repositoryResponse = mock<RepositoryResponse>()
        val repositoryItem = mock<RepositoryItem>()
        val list: List<RepositoryItem> = listOf(repositoryItem)
        whenever(gitHubApi.getRepositories("stars:>=$stars")).thenReturn(Single.just(repositoryResponse))
        whenever(repositoryResponse.items).thenReturn(list)

        var result: List<RepositoryItem> = emptyList()
        classToTest.getRepositories(stars)
            .subscribe(
                { result = it },
                { /* no implementation */ }
            )

        Assertions.assertThat(result).containsExactly(repositoryItem)
    }

    @Test
    fun getDetails() {
        classToTest.getDetails(42)

        verify(gitHubApi).getRepository(42)
    }
}