package com.pegas.aura.aigirlfriend.soul.domain.repository

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.report.CreateReportInput
import com.pegas.aura.aigirlfriend.soul.domain.model.report.Report

interface ReportRepository {
    suspend fun submitReport(input: CreateReportInput): AppResult<Report>
}
