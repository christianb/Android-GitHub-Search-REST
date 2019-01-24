package com.bunk.urbanmobility.api

import com.bunk.urbanmobility.api.entity.RepositoryItem
import com.bunk.urbanmobility.api.entity.RepositoryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String = "stars:>=10000",
        @Query("sort") sort: String = "stars"
    ): Single<RepositoryResponse>

    @GET("repositories/{id}")
    fun getRepository(
        @Path("id") id: Int
    ): Single<RepositoryItem>
}