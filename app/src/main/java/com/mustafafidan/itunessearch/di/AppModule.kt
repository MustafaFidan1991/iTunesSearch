package com.mustafafidan.itunessearch.di

import android.content.Context
import com.mustafafidan.itunessearch.constants.BASE_URL
import com.mustafafidan.itunessearch.feature_search.data.data_source.remote.SearchService
import com.mustafafidan.itunessearch.feature_search.data.repository.SearchRepositoryImpl
import com.mustafafidan.itunessearch.feature_search.data.repository.remote.SearchRemoteRepositoryImpl
import com.mustafafidan.itunessearch.feature_search.domain.repository.SearchRepository
import com.mustafafidan.itunessearch.feature_search.domain.repository.remote.SearchRemoteRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context) : Context{
        return appContext
    }

    @Provides
    fun provideSearchService(moshiConverterFactory: MoshiConverterFactory): SearchService {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build())
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(BASE_URL)
            .build()
            .create(SearchService::class.java)
    }

    @Provides
    fun provideMoshi() : MoshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build())

    @Provides
    fun provideSearchRemoteRepository(searchService : SearchService): SearchRemoteRepository {
        return SearchRemoteRepositoryImpl(searchService)
    }

    @Provides
    fun provideSearchRepository(searchRemoteRepository : SearchRemoteRepository): SearchRepository {
        return SearchRepositoryImpl(searchRemoteRepository)
    }
}