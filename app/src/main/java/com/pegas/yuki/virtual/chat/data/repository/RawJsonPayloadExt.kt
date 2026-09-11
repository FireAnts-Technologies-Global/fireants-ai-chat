package com.pegas.yuki.virtual.chat.data.repository

import com.pegas.yuki.virtual.chat.domain.model.common.RawJsonPayload
import okhttp3.ResponseBody

internal fun ResponseBody.toRawJsonPayload(): RawJsonPayload = use {
    RawJsonPayload(json = it.string())
}
