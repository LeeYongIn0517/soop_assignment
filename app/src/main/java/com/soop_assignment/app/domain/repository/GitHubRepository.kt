package com.soop_assignment.app.domain.repository

import com.soop_assignment.app.data.entity.OwnerAllData
import com.soop_assignment.app.data.entity.RepoMetaData
import com.soop_assignment.app.data.entity.RepoWithoutScore
import com.soop_assignment.app.data.entity.SearchResponse

interface GitHubRepository {
    fun searchRepositories(query: String): SearchResponse?
    fun getRepository(owner: String, repo: String): RepoMetaData?
    fun getUserRepositories(username: String): List<RepoWithoutScore>?
    fun getUserInfo(username: String): OwnerAllData?
}
