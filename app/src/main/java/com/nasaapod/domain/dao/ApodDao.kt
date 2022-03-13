package com.nasaapod.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nasaapod.data.model.ApodResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEntries(list: List<ApodResponse>)

    @Query("SELECT * FROM apod ORDER BY apod.date DESC")
    fun getAll(): Flow<List<ApodResponse>>

    @Query("DELETE FROM apod")
    suspend fun deleteAll()
}