package com.soop_assignment.app.data.service

import com.soop_assignment.app.domain.entity.OwnerAllData
import com.soop_assignment.app.domain.entity.RepoMetaData
import com.soop_assignment.app.domain.entity.RepoWithoutScore
import com.soop_assignment.app.domain.entity.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("repos/{owner}/{repo}")
    fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<RepoMetaData>

    @GET("users/{username}/repos")
    fun getUserRepositories(
        @Path("username") username: String
    ): Call<List<RepoWithoutScore>>

    @GET("users/{username}")
    fun getUserInfo(
        @Path("username") username: String
    ): Call<OwnerAllData>
}
