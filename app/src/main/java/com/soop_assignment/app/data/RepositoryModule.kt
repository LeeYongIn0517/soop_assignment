package com.soop_assignment.app.data

import com.soop_assignment.app.data.repositoryImpl.GitHubRepositoryImpl
import com.soop_assignment.app.domain.repository.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindGitHubRepositoryImpl(repositoryImpl: GitHubRepositoryImpl):GitHubRepository
}
