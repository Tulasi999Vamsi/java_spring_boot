package com.expenses.controller;

import com.expenses.Models.Expense;
import com.expenses.Service.Expense_Service;
import com.expenses.Service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class PdfController {

    @Autowired
    private Expense_Service exp_service;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> getPdf() throws IOException {
        try {
            List<Expense> expenses = exp_service.findall();
            double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();

            byte[] pdfBytes = pdfService.generatePdf(expenses, totalAmount);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=expenses_report.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(byteArrayInputStream));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
