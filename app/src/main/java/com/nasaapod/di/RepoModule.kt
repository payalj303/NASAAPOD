package com.nasaapod.di

import com.nasaapod.data.api.ApiService
import com.nasaapod.domain.ApodDao
import com.nasaapod.domain.repository.ApodRepository
import com.nasaapod.domain.repository.ApodRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideApodRepository(appDao: ApodDao, apodService: ApiService): ApodRepository =
        ApodRepositoryImpl(appDao, apodService)
}