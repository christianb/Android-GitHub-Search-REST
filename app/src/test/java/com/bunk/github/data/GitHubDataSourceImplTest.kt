package com.bunk.github.data

import com.bunk.github.data.entity.RepositoryResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

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
        whenever(gitHubApi.getRepositories("stars:>=$stars")).thenReturn(Single.just(repositoryResponse))

        var result: RepositoryResponse? = null
        classToTest.getRepositories(stars)
            .subscribe(
                { result = it },
                { /* no implementation */ }
            )

        assertThat(result).isEqualTo(repositoryResponse)
    }

    @Test
    fun getDetails() {
        classToTest.getDetails(42)

        verify(gitHubApi).getRepository(42)
    }
}