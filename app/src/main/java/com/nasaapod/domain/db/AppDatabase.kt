package com.nasaapod.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nasaapod.data.model.ApodResponse
import com.nasaapod.domain.ApodDao

@Database(entities = [ApodResponse::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apodListDao(): ApodDao
}