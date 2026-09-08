package com.anrstudio.template.domain.repository

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.report.CreateReportInput
import com.anrstudio.template.domain.model.report.Report

interface ReportRepository {
    suspend fun submitReport(input: CreateReportInput): AppResult<Report>
}
