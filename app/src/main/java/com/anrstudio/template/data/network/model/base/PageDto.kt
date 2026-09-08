package com.anrstudio.template.data.network.model.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageDto<T>(
    @param:Json(name = "items") val items: List<T> = emptyList(),
    @param:Json(name = "total") val total: Int? = null,
    @param:Json(name = "page") val page: Int? = null,
    @param:Json(name = "limit") val limit: Int? = null,
    @param:Json(name = "totalPages") val totalPages: Int? = null,
    @param:Json(name = "hasNext") val hasNext: Boolean? = null,
    @param:Json(name = "hasPrev") val hasPrev: Boolean? = null
)
