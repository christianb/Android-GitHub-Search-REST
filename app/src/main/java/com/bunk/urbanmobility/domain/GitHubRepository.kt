package com.bunk.urbanmobility.domain

import com.bunk.urbanmobility.data.entity.RepositoryItem
import io.reactivex.Single

interface GitHubRepository {
    fun getNextPage(stars: Int): Single<List<RepositoryItem>>
    fun getRepositories(stars: Int): Single<List<RepositoryItem>>
}