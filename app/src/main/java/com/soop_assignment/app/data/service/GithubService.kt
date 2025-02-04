package com.soop_assignment.app.data.service

import com.soop_assignment.app.data.model.OwnerAllData
import com.soop_assignment.app.data.model.RepoMetaData
import com.soop_assignment.app.data.model.RepoWithoutScore
import com.soop_assignment.app.data.model.SearchResponse
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
