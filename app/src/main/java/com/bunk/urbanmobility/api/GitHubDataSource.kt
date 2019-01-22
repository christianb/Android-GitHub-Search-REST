package com.bunk.urbanmobility.api

import com.bunk.urbanmobility.api.entity.RepositoryItem
import io.reactivex.Single

interface GitHubDataSource {
    fun getRepositories(): Single<List<RepositoryItem>>
}