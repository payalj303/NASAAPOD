package com.nasaapod.di

import android.content.Context
import androidx.room.Room
import com.nasaapod.domain.ApodDao
import com.nasaapod.domain.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "nasaApodDatabase.db"
        ).allowMainThreadQueries().build()

    @Provides
    fun provideApodDao(database: AppDatabase): ApodDao =
        database.apodListDao()
}