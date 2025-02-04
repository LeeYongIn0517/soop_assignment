package com.soop_assignment.app.data.service

import com.skydoves.sandwich.ApiResponse
import com.soop_assignment.app.data.model.GitHubOwner
import com.soop_assignment.app.data.model.GitHubRepo
import com.soop_assignment.app.data.model.GitHubSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String
    ): Response<GitHubSearchResponse>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<GitHubRepo>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String
    ): Response<List<GitHubRepo>>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<GitHubOwner>
}
