package com.soop_assignment.app.data.service

import com.soop_assignment.app.data.entity.OwnerAllData
import com.soop_assignment.app.data.entity.RepoMetaData
import com.soop_assignment.app.data.entity.RepoWithoutScore
import com.soop_assignment.app.data.entity.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String
    ): Response<SearchResponse>

    @GET("repos/{owner}/{repo}")
    fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<RepoMetaData>

    @GET("users/{username}/repos")
    fun getUserRepositories(
        @Path("username") username: String
    ): Response<List<RepoWithoutScore>>

    @GET("users/{username}")
    fun getUserInfo(
        @Path("username") username: String
    ): Response<OwnerAllData>
}
