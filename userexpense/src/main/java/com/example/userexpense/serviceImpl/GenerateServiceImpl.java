package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.UserExpensePaymentMode;
import com.example.userexpense.repository.ExcelYearRepository;
import com.example.userexpense.service.GenerateExcelService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public void newReportExcel(){
        workbook = new XSSFWorkbook();
    }
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
        for (UserExpensePaymentMode userExpensePaymentMode:list){
            Row row = sheet.createRow(startRow++);
            int columnCount = 0;
            createCell(row,columnCount++,userExpensePaymentMode.getDescription());
            createCell(row,columnCount++,userExpensePaymentMode.getExpenseType());
            createCell(row,columnCount++,userExpensePaymentMode.getValue());
            createCell(row,columnCount++,userExpensePaymentMode.getExpenseDate());
            createCell(row,columnCount++,userExpensePaymentMode.getPaymentMode());

        }
    }

    public void exportToExcel (HttpServletResponse response,String year) throws IOException {
        // Response write to excel
        List<UserExpensePaymentMode> data = excelYearRepository.earlyExpenseDataToExcel(userLoginId.getUserId(),year);
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
    }

}


