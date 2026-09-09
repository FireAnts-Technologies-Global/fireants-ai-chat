package com.pegas.aura.aigirlfriend.soul.domain.usecase.report

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.report.CreateReportInput
import com.pegas.aura.aigirlfriend.soul.domain.model.report.Report
import com.pegas.aura.aigirlfriend.soul.domain.repository.ReportRepository
import javax.inject.Inject

class SubmitReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(input: CreateReportInput): AppResult<Report> {
        return reportRepository.submitReport(input)
    }
}
