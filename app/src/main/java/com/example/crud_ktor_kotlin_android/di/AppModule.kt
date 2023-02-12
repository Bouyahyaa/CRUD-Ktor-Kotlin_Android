package com.example.crud_ktor_kotlin_android.di

import android.content.SharedPreferences
import com.example.crud_ktor_kotlin_android.BuildConfig
import com.example.crud_ktor_kotlin_android.feature_posts.data.data_source.PostApi
import com.example.crud_ktor_kotlin_android.feature_posts.data.data_source.PostRemoteDataSource
import com.example.crud_ktor_kotlin_android.feature_posts.data.repository.PostsRepositoryImpl
import com.example.crud_ktor_kotlin_android.feature_posts.domain.repository.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val token = sharedPreferences.getString("jwt", "")
                val modifiedRequest = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                it.proceed(modifiedRequest)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun providePostApi(): PostApi {
        return Retrofit.Builder().baseUrl(BuildConfig.SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostApi::class.java)
    }


    @Provides
    @Singleton
    fun providePostRepository(
        postRemoteDataSource: PostRemoteDataSource,
    ): PostsRepository {
        return PostsRepositoryImpl(postRemoteDataSource = postRemoteDataSource)
    }
}