package com.pegas.aura.aigirlfriend.soul.ui.bases.navigation

import android.net.Uri

object AppRoutes {
    const val MAIN = "main"
    const val MAIN_HOME = "main_home"
    const val MAIN_REWARD = "main_reward"
    const val MAIN_CHAT = "main_chat"
    const val MAIN_MISSION = "main_mission"
    const val MAIN_STORE = "main_store"
    const val SUBSCRIPTION = "subscription"
    const val HISTORIES = "histories"

    const val CREATE_CHARACTER = "create_character"
    const val CHARACTER_DETAIL = "character_detail"
    const val CHARACTER_SLUG_ARG = "slug"
    const val CHARACTER_DETAIL_ROUTE = "$CHARACTER_DETAIL/{$CHARACTER_SLUG_ARG}"
    const val CHAT_ROOM = "chat_room"
    const val CONVERSATION_ID_ARG = "conversationId"
    const val CHAT_ROOM_ROUTE = "$CHAT_ROOM/{$CONVERSATION_ID_ARG}"

    fun characterDetail(slug: String): String = "$CHARACTER_DETAIL/${Uri.encode(slug)}"

    fun chatRoom(conversationId: String): String = "$CHAT_ROOM/${Uri.encode(conversationId)}"
}
