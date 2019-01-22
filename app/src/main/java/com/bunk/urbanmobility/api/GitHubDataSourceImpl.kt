package com.bunk.urbanmobility.api

import com.bunk.urbanmobility.api.entity.RepositoryItem
import io.reactivex.Single

class GitHubDataSourceImpl(
    private val gitHubApi: GitHubApi
) : GitHubDataSource {
    
    override fun getRepositories(): Single<List<RepositoryItem>> =
        gitHubApi.getRepositories()
            .map { it.items }
}