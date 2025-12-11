package com.example.userexpense.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public interface GenerateExcelService {
    public HttpServletResponse initResponseForExportExcel (HttpServletResponse response,String fileName);
    public void writeTableHeaderExcel(String sheetName, String titleName, String[] headers);
    public void createCell(Row row, int columnCount, Object value);
    public void writeTableDataToExcel (Object data);
    public void exportToExcel (HttpServletResponse response,String year) throws IOException;
}