package com.pegas.aura.aigirlfriend.soul.domain.model.conversation

import com.pegas.aura.aigirlfriend.soul.domain.model.common.PageEntity

data class QuickPrompt(
    val id: String,
    val icon: String,
    val coinCost: Int,
    val title: String,
    val content: String,
    val sortOrder: Int
)

typealias QuickPromptPage = PageEntity<QuickPrompt>
