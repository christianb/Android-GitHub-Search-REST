package com.bunk.urbanmobility.api

import com.bunk.urbanmobility.api.entity.RepositoryItem
import io.reactivex.Single

class GitHubDataSourceImpl(
    private val gitHubApi: GitHubApi
) : GitHubDataSource {

    override fun getRepositories(stars: Int): Single<List<RepositoryItem>> {
        return gitHubApi.getRepositories("stars:>=$stars").map { it.items }
    }

    override fun getDetails(id: Int): Single<RepositoryItem> =
        gitHubApi.getRepository(id)
}