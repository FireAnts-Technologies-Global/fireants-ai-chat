package com.anrstudio.template.data.network.service

import com.anrstudio.template.data.network.model.report.CreateReportRequestDto
import com.anrstudio.template.data.network.model.report.ReportResponseEnvelopeDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ReportService {
    @Headers("Content-Type: application/json")
    @POST("reports")
    suspend fun createReport(
        @Body body: CreateReportRequestDto
    ): ReportResponseEnvelopeDto
}
