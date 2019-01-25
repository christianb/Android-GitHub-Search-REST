package com.bunk.github.domain

import com.bunk.github.data.entity.RepositoryItem
import io.reactivex.Single

interface GitHubRepository {
    fun getNextRepositoryPage(stars: Int): Single<List<RepositoryItem>>
    fun getRepositories(stars: Int): Single<List<RepositoryItem>>
    fun getDetails(id: Int): Single<RepositoryItem>
}