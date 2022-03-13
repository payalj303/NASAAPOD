package com.nasaapod.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = "apod")
data class ApodResponse(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("id")
    val copyright: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("hdurl")
    val hdurl: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("service_version")
    val serviceVersion: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
) : Parcelable {}