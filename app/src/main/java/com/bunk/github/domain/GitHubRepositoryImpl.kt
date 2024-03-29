package com.bunk.github.domain

import com.bunk.github.data.GitHubDataSource
import com.bunk.github.data.entity.RepositoryItem
import io.reactivex.Single

private const val DEFAULT_PAGE = 1

class GitHubRepositoryImpl(
    private val gitHubDataSource: GitHubDataSource
) : GitHubRepository {

    private var page = DEFAULT_PAGE

    override fun getNextRepositoryPage(stars: Int): Single<List<RepositoryItem>> {
        return getRepositories(stars, page = ++page)
    }

    override fun getRepositories(stars: Int): Single<List<RepositoryItem>> {
        page = DEFAULT_PAGE
        return getRepositories(stars, page = page)
    }

    override fun getDetails(id: Int): Single<RepositoryItem> {
        return gitHubDataSource.getDetails(id)
    }

    private fun getRepositories(stars: Int, page: Int): Single<List<RepositoryItem>> {
        return gitHubDataSource.getRepositories(stars, page = page).map { it.items }
    }
}