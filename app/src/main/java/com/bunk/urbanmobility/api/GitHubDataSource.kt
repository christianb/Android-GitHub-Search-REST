package com.bunk.urbanmobility.api

import com.bunk.urbanmobility.api.entity.RepositoryItem
import io.reactivex.Single

interface GitHubDataSource {
    fun getRepositories(stars: Int): Single<List<RepositoryItem>>
    fun getDetails(id: Int): Single<RepositoryItem>
}