// Dung POI  (poi-ooxml la dang moi cua poi)chuan nhung code dai, dung EasyExcel code ngan hon nhung khong co cac chuc nang mau me 
package com.example.demo.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BackupHistory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class BackupExportService {

    public ByteArrayInputStream exportToExcel(List<BackupHistory> backups) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Backups");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Thời điểm tạo");
            header.createCell(2).setCellValue("Người thực hiện");
            header.createCell(3).setCellValue("Đường dẫn");
            header.createCell(4).setCellValue("Ghi chú");

            // Data rows
            int rowIdx = 1;
            for (BackupHistory b : backups) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(b.getId().toString());
                row.createCell(1).setCellValue(b.getBackupTime());
                row.createCell(2).setCellValue(b.getUser().getEmail());
                row.createCell(3).setCellValue(b.getBackupPath());
                row.createCell(4).setCellValue(b.getNote());
            }

            // Ghi ra ByteArrayOutputStream
            workbook.write(byteArrayOut);

            // Ghi xuống file (optional)
            try (FileOutputStream fileOut = new FileOutputStream("D:/backup_history.xlsx")) {
                fileOut.write(byteArrayOut.toByteArray());
            }

            return new ByteArrayInputStream(byteArrayOut.toByteArray());
        }
    }
}