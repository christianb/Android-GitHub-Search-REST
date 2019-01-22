package com.bunk.urbanmobility.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String = "stars:>=10000",
        @Query("sort") sort: String = "stars"
    ): Single<RepositoryResponse>
}