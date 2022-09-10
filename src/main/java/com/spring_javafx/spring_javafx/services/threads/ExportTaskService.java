package com.spring_javafx.spring_javafx.services.threads;

import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.files.ExcelFile;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Service
public class ExportTaskService extends Service<Sheet> {
    private Workbook workbook;
    private ArrayList<PatientVo> list;
    private final int numberOfTitles = 9;
    private static ApplicationContext applicationContext;

    public ExportTaskService getMyClass(){
        // Now @Autowired MyRepository will work
        return new ExportTaskService();
    }
    public void getPatientsAndWorkbook(ArrayList<PatientVo> list, Workbook workbook){
        this.list = list;
        this.workbook = workbook;
    }

    final private String[] excelColumns = {"Nº Cliente", "Nombre", "Apellido", "Sexo", "Cumpleaños", "Telefono", "Email", "Nota", "Fecha"};

    @Lazy
    @Autowired
    private ExcelFile excelFile;

    public Font setFontToHeader(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Calibri");
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        return headerFont;
    }

    public Font setFontToTitle(Workbook workbook) {
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontName("Calibri");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        return titleFont;
    }

    public void makeHeaderRow(Workbook workbook, Row headerRow) {
        Font headerFont = this.setFontToHeader(workbook);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        for (int i = 0; i < excelColumns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelColumns[i]);

            if (i == 0) {
                CellStyle firstCellStyle = workbook.createCellStyle();
                firstCellStyle.setBorderLeft(BorderStyle.MEDIUM);
                firstCellStyle.setBorderTop(BorderStyle.MEDIUM);
                firstCellStyle.setBorderBottom(BorderStyle.MEDIUM);
                cell.setCellStyle(firstCellStyle);

            }
            if (i == excelColumns.length - 1) {
                CellStyle lastCellStyle = workbook.createCellStyle();
                lastCellStyle.setBorderRight(BorderStyle.MEDIUM);
                lastCellStyle.setBorderTop(BorderStyle.MEDIUM);
                lastCellStyle.setBorderBottom(BorderStyle.MEDIUM);
                cell.setCellStyle(lastCellStyle);
            } else {
                headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
                headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
                cell.setCellStyle(headerCellStyle);
            }
        }
    }

    public void printTitle(Workbook workbook, Sheet sheet, Row row) {
        String title = "BASE DE DATOS PACIENTES CEMERESI";
        Font titleFont = this.setFontToTitle(workbook);
        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setFillBackgroundColor((short) 2);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFont(titleFont);
        CellRangeAddress range = new CellRangeAddress(1, 1, 0, excelColumns.length - 1);

        // Creates the cell
        CellUtil.createCell(row, sheet.addMergedRegion(range), title, titleCellStyle);
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected XSSFSheet call() throws Exception {
                Sheet sheet = workbook.createSheet();
                Row titleRow = sheet.createRow(1);
                Row headerRow = sheet.createRow(4);
                printTitle( workbook,sheet,titleRow);
                makeHeaderRow( workbook,headerRow);
                AtomicInteger index = new AtomicInteger(5);

                list.forEach(client -> {

                    String birthday = "";
                    String date = "";

                    //Converting the Date object to String format
                    if (client.getDate() != null) {
                        Date dateObj = client.getDate();
                        date = dateObj.toString();
                    }
                    if (client.getBirthday() != null) {
                        Date birthdayObj = client.getBirthday();
                        birthday = birthdayObj.toString();
                    }
                    Row row = sheet.createRow(index.get());
                    row.createCell(0).setCellValue(String.valueOf(client.getId()));
                    row.createCell(1).setCellValue(String.valueOf(client.getName()));
                    row.createCell(2).setCellValue(String.valueOf(client.getLastName()));
                    row.createCell(3).setCellValue(String.valueOf(client.getSex()));
                    row.createCell(4).setCellValue(birthday);
                    row.createCell(5).setCellValue(String.valueOf(client.getPhone()));
                    row.createCell(6).setCellValue(String.valueOf(client.getEmail()));
                    row.createCell(7).setCellValue(String.valueOf(client.getNote()));
                    row.createCell(8).setCellValue(date);

                    index.getAndIncrement();
                });
                for(int i = 0; i < numberOfTitles; i++) {
                    sheet.autoSizeColumn(i);
                }
                return (XSSFSheet) sheet;
            }
        };
    }

}
