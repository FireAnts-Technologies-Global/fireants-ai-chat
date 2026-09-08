package com.anrstudio.template.domain.model.common

data class PageEntity<T>(
    val items: List<T>,
    val total: Int,
    val page: Int,
    val limit: Int,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrev: Boolean
)
