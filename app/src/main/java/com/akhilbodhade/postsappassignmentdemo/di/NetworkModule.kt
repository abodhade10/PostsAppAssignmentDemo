package com.akhilbodhade.postsappassignmentdemo.di

import com.akhilbodhade.postsappassignmentdemo.data.remote.PostApi
import com.akhilbodhade.postsappassignmentdemo.data.remote.PostApi.Companion.BASE_URL
import com.akhilbodhade.postsappassignmentdemo.data.repository.PostsRepositoryImpl
import com.akhilbodhade.postsappassignmentdemo.domain.repository.PostsRepository
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
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostsRepository(postApi: PostApi): PostsRepository {
        return PostsRepositoryImpl(postApi)
    }
}