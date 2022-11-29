package com.greedobank.reports.controller;

import com.greedobank.reports.service.ReportServiceBase;
import com.greedobank.reports.service.ReportServiceDaily;
import com.greedobank.reports.service.ReportServiceUnpublished;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
public class ReportController {
    private final ReportServiceBase reportService;

    @Autowired
    public ReportController(ReportServiceUnpublished reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/api/v1/report")
    @ResponseBody
    @Operation(summary = "Create report", description = "Create report")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<byte[]> serveFile() throws IOException {
        var report = reportService.generateXlsxReport();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
                .body(report);
    }
}
