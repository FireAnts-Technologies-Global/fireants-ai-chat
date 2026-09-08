package com.anrstudio.template.domain.usecase.report

import com.anrstudio.template.domain.model.common.AppResult
import com.anrstudio.template.domain.model.report.CreateReportInput
import com.anrstudio.template.domain.model.report.Report
import com.anrstudio.template.domain.repository.ReportRepository
import javax.inject.Inject

class SubmitReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(input: CreateReportInput): AppResult<Report> {
        return reportRepository.submitReport(input)
    }
}
