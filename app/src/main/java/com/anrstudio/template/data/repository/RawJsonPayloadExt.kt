package com.pegas.aura.aigirlfriend.soul.data.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.common.RawJsonPayload
import okhttp3.ResponseBody

internal fun ResponseBody.toRawJsonPayload(): RawJsonPayload = use {
    RawJsonPayload(json = it.string())
}
