package com.pegas.aura.aigirlfriend.soul.data.network.model.conversation

import com.pegas.aura.aigirlfriend.soul.data.network.model.base.toDomain
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.QuickPrompt
import com.pegas.aura.aigirlfriend.soul.domain.model.conversation.QuickPromptPage

fun QuickPromptPageDto.toDomain(): QuickPromptPage = toDomain { it.toDomain() }

fun QuickPromptDto.toDomain(): QuickPrompt = QuickPrompt(
    id = id.orEmpty(),
    icon = iconUrl ?: icon.orEmpty(),
    coinCost = coinCost ?: 0,
    title = title.orEmpty(),
    content = content.orEmpty(),
    sortOrder = sortOrder ?: 0
)
