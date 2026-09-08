package com.anrstudio.template.data.network.model.conversation

import com.anrstudio.template.data.network.model.base.toDomain
import com.anrstudio.template.domain.model.conversation.QuickPrompt
import com.anrstudio.template.domain.model.conversation.QuickPromptPage

fun QuickPromptPageDto.toDomain(): QuickPromptPage = toDomain { it.toDomain() }

fun QuickPromptDto.toDomain(): QuickPrompt = QuickPrompt(
    id = id.orEmpty(),
    icon = iconUrl ?: icon.orEmpty(),
    coinCost = coinCost ?: 0,
    title = title.orEmpty(),
    content = content.orEmpty(),
    sortOrder = sortOrder ?: 0
)
