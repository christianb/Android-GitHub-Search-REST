package com.bunk.urbanmobility.data

import com.bunk.urbanmobility.data.entity.RepositoryItem
import com.bunk.urbanmobility.data.entity.RepositoryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String = "stars:>=10000",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int = 1
    ): Single<RepositoryResponse>

    @GET("repositories/{id}")
    fun getRepository(
        @Path("id") id: Int
    ): Single<RepositoryItem>
}