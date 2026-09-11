package com.pegas.yuki.virtual.chat.ui.analytics

import com.pegas.yuki.virtual.chat.domain.model.common.PublicError
import com.pegas.yuki.virtual.chat.domain.model.conversation.QuickPrompt
import com.pegas.yuki.virtual.chat.utils.PurchaseTracking
import javax.inject.Inject

class ChatActionTracker @Inject constructor() {
    fun trackIntent(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt,
        coinBalance: Int
    ) {
        PurchaseTracking.chatActionIntent(
            screen = screen,
            characterId = characterId,
            prompt = prompt,
            coinBalance = coinBalance
        )
    }

    fun trackSuccess(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt?,
        promptId: String,
        coinBalance: Int
    ) {
        PurchaseTracking.chatActionSuccess(
            screen = screen,
            characterId = characterId,
            prompt = prompt,
            promptId = promptId,
            coinBalance = coinBalance
        )
    }

    fun trackFailure(
        screen: String,
        characterId: String?,
        prompt: QuickPrompt?,
        promptId: String,
        coinBalance: Int,
        error: PublicError
    ) {
        PurchaseTracking.chatActionFail(
            screen = screen,
            characterId = characterId,
            prompt = prompt,
            promptId = promptId,
            coinBalance = coinBalance,
            error = error
        )
    }
}
