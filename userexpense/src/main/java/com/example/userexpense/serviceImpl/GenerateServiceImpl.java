package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.UserExpensePaymentMode;
import com.example.userexpense.repository.ExcelYearRepository;
import com.example.userexpense.service.GenerateExcelService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class GenerateServiceImpl implements GenerateExcelService {
    @Autowired
    ExcelYearRepository excelYearRepository;
    @Autowired
    UserLoginId userLoginId;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;

//
    @Override
    public HttpServletResponse initResponseForExportExcel(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        return response;
    }

    @Override
    public void writeTableHeaderExcel(String sheetName, String titleName, String[] headers) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
    }

    @Override
    public void createCell(Row row, int columnCount, Object value) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        }
        else if (value instanceof Double){
            cell.setCellValue((Double) value);
        }
        else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }
        else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
    }

    @Override
    public void writeTableDataToExcel(Object data) {
        List<UserExpensePaymentMode> list = (List<UserExpensePaymentMode>) data;
        // starting write on row
        int startRow = 2;
        // Writing the content
        for (UserExpensePaymentMode ue : list) {
            XSSFRow row = sheet.createRow(startRow++);
            int columnCount = 0;

            row.createCell(columnCount++).setCellValue(ue.getDescription());
            row.createCell(columnCount++).setCellValue(ue.getExpenseType());
            row.createCell(columnCount++).setCellValue(ue.getValue());

            // Here is where you need to format LocalDate
            row.createCell(columnCount++).setCellValue(
                    ue.getExpenseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            );

            row.createCell(columnCount++).setCellValue(ue.getPaymentMode());
        }
    }

    public void exportToExcel (HttpServletResponse response,Integer year) throws IOException {
        // Response write to excel


        try{
            this.workbook = new XSSFWorkbook();
            // System.out.println("Date is"+" "+date);
            this.sheet = workbook.createSheet("Sheet User");
            List<UserExpensePaymentMode> data = excelYearRepository.earlyExpenseDataToExcel(userLoginId.getUserId(),year);

            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            );
            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=UserExcel_Download_" + LocalDate.now() + ".xlsx"
            );

            response = initResponseForExportExcel(response,"UserExcel");
            ServletOutputStream outputStream = response.getOutputStream();

            // Write Sheet,Title and Header
            String [] headers = {"Description","Expense Type","Value","Expense Date","Payment Mode"};
            writeTableHeaderExcel("Sheet User","Report User",headers);

            // write content row
                writeTableDataToExcel(data);
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();

            System.out.println("---- EXCEL CONTENT START ----");

            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;

                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                System.out.print(cell.getLocalDateTimeCellValue() + "\t");
                            } else {
                                System.out.print(cell.getNumericCellValue() + "\t");
                            }
                            break;

                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;

                        default:
                            System.out.print(" \t");
                    }
                }
                System.out.println();
            }

            System.out.println("---- EXCEL CONTENT END ----");

        }
        catch(Exception e){
            System.out.println("Exception in Generating Excel"+" "+e);
        }
    }

}


