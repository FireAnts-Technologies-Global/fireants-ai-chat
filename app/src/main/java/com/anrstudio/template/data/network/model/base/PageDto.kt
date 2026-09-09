package com.pegas.aura.aigirlfriend.soul.data.network.model.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageDto<T>(
    @Json(name = "items") val items: List<T> = emptyList(),
    @Json(name = "total") val total: Int? = null,
    @Json(name = "page") val page: Int? = null,
    @Json(name = "limit") val limit: Int? = null,
    @Json(name = "totalPages") val totalPages: Int? = null,
    @Json(name = "hasNext") val hasNext: Boolean? = null,
    @Json(name = "hasPrev") val hasPrev: Boolean? = null
)
