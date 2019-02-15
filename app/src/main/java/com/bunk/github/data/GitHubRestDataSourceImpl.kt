package com.bunk.github.data

import com.bunk.github.data.entity.RepositoryItem
import com.bunk.github.data.entity.RepositoryResponse
import io.reactivex.Single

class GitHubRestDataSourceImpl(
    private val gitHubApi: GitHubApi
) : GitHubRestDataSource {

    override fun getRepositories(stars: Int, page: Int): Single<RepositoryResponse> {
        return gitHubApi.getRepositories("stars:>=$stars", page = page)
    }

    override fun getDetails(id: Int): Single<RepositoryItem> =
        gitHubApi.getRepository(id)
}