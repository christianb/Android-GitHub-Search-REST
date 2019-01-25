package com.bunk.urbanmobility.domain

import com.bunk.urbanmobility.data.GitHubDataSource
import com.bunk.urbanmobility.data.entity.RepositoryItem
import io.reactivex.Single

private const val DEFAULT_PAGE = 1

class GitHubRepositoryImpl(
    private val gitHubDataSource: GitHubDataSource
) : GitHubRepository {

    private var page = DEFAULT_PAGE

    override fun getNextPage(stars: Int): Single<List<RepositoryItem>> {
        return getRepositories(stars, page = ++page)
    }

    override fun getRepositories(stars: Int): Single<List<RepositoryItem>> {
        page = DEFAULT_PAGE
        return getRepositories(stars, page = page)
    }

    private fun getRepositories(stars: Int, page: Int): Single<List<RepositoryItem>> {
        return gitHubDataSource.getRepositories(stars, page = page).map { it.items }
    }
}