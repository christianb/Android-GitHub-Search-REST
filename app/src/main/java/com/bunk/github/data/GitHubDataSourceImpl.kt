package com.bunk.github.data

import com.bunk.github.data.entity.RepositoryItem
import com.bunk.github.data.entity.RepositoryResponse
import io.reactivex.Single

class GitHubDataSourceImpl(
    private val gitHubApi: GitHubApi
) : GitHubDataSource {

    override fun getRepositories(stars: Int, page: Int): Single<RepositoryResponse> {
        return gitHubApi.getRepositories("stars:>=$stars", page = page)
    }

    override fun getDetails(id: Int): Single<RepositoryItem> =
        gitHubApi.getRepository(id)
}