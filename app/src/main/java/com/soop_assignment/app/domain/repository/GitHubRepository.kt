package com.soop_assignment.app.domain.repository

import com.soop_assignment.app.data.model.OwnerAllData
import com.soop_assignment.app.data.model.RepoMetaData
import com.soop_assignment.app.data.model.RepoWithoutScore
import com.soop_assignment.app.data.model.SearchResponse

interface GitHubRepository {
    fun searchRepositories(query: String): SearchResponse?
    fun getRepository(owner: String, repo: String): RepoMetaData?
    fun getUserRepositories(username: String): List<RepoWithoutScore>?
    fun getUserInfo(username: String): OwnerAllData?
}
