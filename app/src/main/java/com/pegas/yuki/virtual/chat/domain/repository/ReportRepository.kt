package com.pegas.yuki.virtual.chat.domain.repository

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.report.CreateReportInput
import com.pegas.yuki.virtual.chat.domain.model.report.Report

interface ReportRepository {
    suspend fun submitReport(input: CreateReportInput): AppResult<Report>
}
