package com.example.krishinetworkassignment.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Response(
    val code: Int? = null,
    val data: Data? = null,
    val status: String? = null
)

data class Data(
    val favMandi: List<Any?>? = null,
    val otherMandi: List<OtherMandiItem?>? = null
)

@Entity
data class OtherMandiItem(
    val image: String? = null,
    val urlStr: String? = null,
    val km: Double? = null,
    val lng: Double? = null,
    val hindiName: String? = null,
    val cropId: Int? = null,
    val lastDate: String? = null,
    val meters: Double? = null,
    val market: String? = null,
    val district: String? = null,
    val location: String? = null,
    val districtId: Int? = null,

    @PrimaryKey
    val id: Int? = null,
    val state: String? = null,
    val lat: Double? = null
)

