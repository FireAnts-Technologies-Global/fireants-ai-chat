package com.anrstudio.template.data.repository

import com.anrstudio.template.domain.model.common.RawJsonPayload
import okhttp3.ResponseBody

internal fun ResponseBody.toRawJsonPayload(): RawJsonPayload = use {
    RawJsonPayload(json = it.string())
}
