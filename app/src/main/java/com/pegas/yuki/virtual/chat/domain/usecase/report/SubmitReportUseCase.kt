package com.pegas.yuki.virtual.chat.domain.usecase.report

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.report.CreateReportInput
import com.pegas.yuki.virtual.chat.domain.model.report.Report
import com.pegas.yuki.virtual.chat.domain.repository.ReportRepository
import javax.inject.Inject

class SubmitReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(input: CreateReportInput): AppResult<Report> {
        return reportRepository.submitReport(input)
    }
}
