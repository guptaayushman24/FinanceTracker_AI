package com.example.userexpense.controller;

import com.example.userexpense.dto.ExcelYearRequestdto;
import com.example.userexpense.dto.ExcelYearResponsedto;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.GenerateExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GenerateExcel {
    @Autowired
    GenerateExcelService generateExcelService;
    @Autowired
    ExtractUserId extractUserId;
    @PostMapping("/yearexpenseexcel")
    public ResponseEntity<ExcelYearResponsedto> exportToExcel(@RequestBody ExcelYearRequestdto excelYearRequestdto, HttpServletResponse response, @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        ExcelYearResponsedto excelYearResponsedto = new ExcelYearResponsedto();
        generateExcelService.exportToExcel(response, excelYearRequestdto.getYear(),excelYearRequestdto.getMonthName(),userId);
        excelYearResponsedto.setMessage("Excel file downloaded");
        return ResponseEntity.ok(excelYearResponsedto);
    }
}
