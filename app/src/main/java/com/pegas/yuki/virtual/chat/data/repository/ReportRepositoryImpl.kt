package com.pegas.yuki.virtual.chat.data.repository

import com.pegas.yuki.virtual.chat.data.network.model.report.ClientMetaDto
import com.pegas.yuki.virtual.chat.data.network.model.report.CreateReportRequestDto
import com.pegas.yuki.virtual.chat.data.network.model.report.ReportResponseEnvelopeDto
import com.pegas.yuki.virtual.chat.data.network.service.ReportService
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.report.CreateReportInput
import com.pegas.yuki.virtual.chat.domain.model.report.Report
import com.pegas.yuki.virtual.chat.domain.repository.ReportRepository
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
