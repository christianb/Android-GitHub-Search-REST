package com.bunk.github.data

import com.bunk.github.data.entity.RepositoryItem
import com.bunk.github.data.entity.RepositoryResponse
import io.reactivex.Single

interface GitHubDataSource {
    fun getRepositories(stars: Int, page: Int = 1): Single<RepositoryResponse>
    fun getDetails(id: Int): Single<RepositoryItem>
}