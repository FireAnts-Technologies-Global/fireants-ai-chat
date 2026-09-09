package com.pegas.aura.aigirlfriend.soul.data.network.model.base

import com.pegas.aura.aigirlfriend.soul.domain.model.common.PageEntity

fun <T, R> PageDto<T>.toDomain(mapItem: (T) -> R): PageEntity<R> = PageEntity(
    items = items.map(mapItem),
    total = total ?: 0,
    page = page ?: 1,
    limit = limit ?: 0,
    totalPages = totalPages ?: 0,
    hasNext = hasNext == true,
    hasPrev = hasPrev == true
)
