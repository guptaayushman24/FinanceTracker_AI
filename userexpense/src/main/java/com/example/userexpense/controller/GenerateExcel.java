package com.example.userexpense.controller;

import com.example.userexpense.dto.ExcelYearRequestdto;
import com.example.userexpense.dto.ExcelYearResponsedto;
import com.example.userexpense.service.GenerateExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateExcel {
    @Autowired
    GenerateExcelService generateExcelService;
    @PostMapping("/yearexpenseexcel")
    public ResponseEntity<ExcelYearResponsedto> exportToExcel(@RequestBody ExcelYearRequestdto excelYearRequestdto, HttpServletResponse response){
        try{
            ExcelYearResponsedto excelYearResponsedto = new ExcelYearResponsedto();
            generateExcelService.exportToExcel(response, excelYearRequestdto.getYear());
            excelYearResponsedto.setMessage("Excel file downloaded");
            return ResponseEntity.ok(excelYearResponsedto);

        }
        catch(Exception e){
            // e.printStackTrace();
            System.out.println("Error is ::::::::"+" "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
