package com.example.HealthCare.Controllers;

import com.example.HealthCare.Services.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping("/simple")
    public ResponseEntity<InputStreamResource> downloadSimpleReport(
            @RequestParam(defaultValue = "Rapport Simple") String title,
            @RequestParam(defaultValue = "Ceci est un rapport généré automatiquement.") String content) {
        
        ByteArrayInputStream bis = pdfGeneratorService.generateSimpleReportPdf(title, content);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=rapport_simple.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
