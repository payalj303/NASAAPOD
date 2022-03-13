package com.nasaapod.domain.repository

import com.nasaapod.data.api.ApiService
import com.nasaapod.data.model.ApodResponse
import com.nasaapod.domain.ApodDao
import com.nasaapod.utils.Resource
import com.nasaapod.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val appDao: ApodDao,
    private val apodService: ApiService
) : ApodRepository {

    override fun getLatestApods(
        isRefresh: Boolean,
        param: Map<String, String>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ): Flow<Resource<List<ApodResponse>>> =
        networkBoundResource(
            loadFromDb = {
                appDao.getAll()
            },
            createCall = {
                apodService.getApodList(param)
            },
            saveToDb = { entities ->
                appDao.deleteAll()
                appDao.saveEntries(entities)

            },
            shouldFetch = {
                isRefresh
            },
            onCallSuccess = onSuccess,
            onCallFailed = onError
        )
}