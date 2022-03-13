package com.nasaapod.domain.repository

import com.nasaapod.data.model.ApodResponse
import com.nasaapod.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ApodRepository {
    fun getLatestApods(
        isRefresh: Boolean,
        param: Map<String, String>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ): Flow<Resource<List<ApodResponse>>>
}