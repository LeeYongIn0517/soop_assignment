package com.soop_assignment.app.data.service

import com.soop_assignment.app.domain.entity.OwnerAllData
import com.soop_assignment.app.domain.entity.RepoMetaData
import com.soop_assignment.app.domain.entity.RepoWithoutScore
import com.soop_assignment.app.domain.entity.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<SearchResponse>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<RepoMetaData>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String
    ): Response<List<RepoWithoutScore>>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<OwnerAllData>
}
