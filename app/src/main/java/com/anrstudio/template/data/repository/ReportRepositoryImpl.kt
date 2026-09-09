package com.pegas.aura.aigirlfriend.soul.data.repository

import com.pegas.aura.aigirlfriend.soul.data.network.model.report.ClientMetaDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.report.CreateReportRequestDto
import com.pegas.aura.aigirlfriend.soul.data.network.model.report.ReportResponseEnvelopeDto
import com.pegas.aura.aigirlfriend.soul.data.network.service.ReportService
import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.report.CreateReportInput
import com.pegas.aura.aigirlfriend.soul.domain.model.report.Report
import com.pegas.aura.aigirlfriend.soul.domain.repository.ReportRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepositoryImpl @Inject constructor(
    private val reportService: ReportService
) : ReportRepository {

    override suspend fun submitReport(input: CreateReportInput): AppResult<Report> =
        apiResult("submitReport") {
            val dto = CreateReportRequestDto(
                reason = input.reason,
                description = input.description,
                clientMeta = ClientMetaDto(
                    appVersion = input.clientMeta.appVersion,
                    platform = input.clientMeta.platform,
                    screen = input.clientMeta.screen
                ),
                conversationId = input.conversationId,
                messageId = input.messageId
            )
            reportService.createReport(dto).requireData().let {
                Report(
                    id = it.id,
                    reason = it.reason,
                    status = it.status,
                    createdAt = it.createdAt
                )
            }
        }
}

private fun ReportResponseEnvelopeDto.requireData() =
    data ?: error(message ?: "Missing report response data")
