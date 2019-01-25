package com.bunk.github.domain

import com.bunk.github.data.entity.RepositoryItem
import io.reactivex.Single

interface GitHubRepository {
    fun getNextPage(stars: Int): Single<List<RepositoryItem>>
    fun getRepositories(stars: Int): Single<List<RepositoryItem>>
}