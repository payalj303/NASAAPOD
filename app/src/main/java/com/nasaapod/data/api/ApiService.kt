package com.nasaapod.data.api

import com.nasaapod.data.model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.nasa.gov/"
    }

    @GET("planetary/apod")
    suspend fun getApodList(@QueryMap params: Map<String, String>): List<ApodResponse>
}