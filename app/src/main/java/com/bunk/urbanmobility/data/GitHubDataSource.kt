package com.bunk.urbanmobility.data

import com.bunk.urbanmobility.data.entity.RepositoryItem
import com.bunk.urbanmobility.data.entity.RepositoryResponse
import io.reactivex.Single

interface GitHubDataSource {
    fun getRepositories(stars: Int, page: Int = 1): Single<RepositoryResponse>
    fun getDetails(id: Int): Single<RepositoryItem>
}