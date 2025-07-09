package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.BackupHistory;
import com.example.demo.entity.Users;
import com.example.demo.repository.BackupHistoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BackupExportService;

@RestController
@RequestMapping("/backup-history")
@CrossOrigin(origins = "*")
public class BackupHistoryController {
    @Autowired
    BackupHistoryRepository backupHistoryRepository;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    BackupExportService backupExportService;

    // Hiển thị tất cả các bản ghi backup history
    @GetMapping("/all")
    public List<BackupHistory> findAll(){
        return backupHistoryRepository.findAll();
    }

    // Them bản ghi backup history mới
    @PostMapping("/save")
    public BackupHistory save(@RequestBody BackupHistory backupHistory){
        if(usersRepository.existsById(backupHistory.getUserId())) {
            return backupHistoryRepository.save(backupHistory);
        }else{
            throw new RuntimeException("Backup history with ID " + backupHistory.getId() + " does not exist.");
        }
    }

    // Tim kiếm bản ghi backup history theo backupTime
    @GetMapping("/time/{backupTime}")
    // Ví dụ: http://localhost:8080/backup-history/time/2025-07-02 14:30:00.000
    public List<BackupHistory> findByBackupTime(@PathVariable String backupTime){
        return backupHistoryRepository.findByBackupTime(backupTime);
    }
    
    // Tim kiếm bản ghi backup history theo email 
    // http://localhost:8080/backup-history/email/dhh1@gmail.com
    @GetMapping("/email/{email}")
    public List<BackupHistory> findByUserId(@PathVariable String email){
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User with email " + email + " does not exist.");
        }
        UUID userId = user.getId();        
        return backupHistoryRepository.findByUserId(userId);
    }

    // Tim kiếm bản ghi backup history theo note
    @GetMapping("/note/{note}")
    // Ví dụ: http://localhost:8080/backup-history/note/Backup%
    public List<BackupHistory> findByNote(@PathVariable String note){
        return backupHistoryRepository.findByNote(note);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BackupHistory> updateBackupHistory(@PathVariable UUID id, @RequestBody BackupHistory backupHistory) {
        
        // Kiểm tra xem bản ghi có tồn tại không
        Optional<BackupHistory> existing = backupHistoryRepository.findById(id);
        
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Đảm bảo ID khớp
        if (!backupHistory.getId().equals(id)) {
            return ResponseEntity.badRequest().build(); // sai ID
        }

        BackupHistory updated = backupHistoryRepository.saveAndFlush(backupHistory); 
        return ResponseEntity.ok(updated); 
    }

    // Xóa bản ghi backup history
    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable UUID id){
        backupHistoryRepository.deleteById(id);
    }

    @GetMapping("/backups/export")
    // http://localhost:8080/backup-history/backups/export
    public ResponseEntity<InputStreamResource> exportToExcel() throws Exception {
        List<BackupHistory> backups = backupHistoryRepository.findAll(); 
        ByteArrayInputStream in = backupExportService.exportToExcel(backups); 

        HttpHeaders headers = new HttpHeaders(); 
        headers.add("Content-Disposition", "attachment; filename=backup_history.xlsx"); 

        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(new InputStreamResource(in));
    }

}
