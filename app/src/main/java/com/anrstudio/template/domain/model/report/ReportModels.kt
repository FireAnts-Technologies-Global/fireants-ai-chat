package com.pegas.aura.aigirlfriend.soul.domain.model.report

data class CreateReportInput(
    val reason: String,
    val description: String,
    val clientMeta: ReportClientMeta,
    val conversationId: String? = null,
    val messageId: String? = null
)

data class ReportClientMeta(
    val appVersion: String,
    val platform: String,
    val screen: String
)

data class Report(
    val id: String,
    val reason: String,
    val status: String,
    val createdAt: String
)
