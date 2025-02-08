package com.soop_assignment.app.data.di

import com.google.gson.GsonBuilder
import com.soop_assignment.app.BuildConfig
import com.soop_assignment.app.data.service.GitHubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("Accept", "application/vnd.github+json")
                    .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN}")
                    .addHeader("X-GitHub-Api-Version", "2022-11-28")
                    .build()
                proceed(newRequest)
            }
        }
    }

    @Provides
    fun provideGitHubService(retrofit: Retrofit): GitHubApiService {
        return retrofit.create(GitHubApiService::class.java)
    }
}
