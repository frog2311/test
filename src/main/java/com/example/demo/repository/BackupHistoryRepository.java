package com.example.demo.repository;
import java.util.List;
// import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BackupHistory;

@Repository
public interface BackupHistoryRepository extends JpaRepository<BackupHistory, UUID> {
    // Hiển thị tất cả các bản ghi backup history
    public List<BackupHistory> findAll();

    // Them bản ghi backup history mới
    public BackupHistory save(BackupHistory backupHistory);

    // Sua bản ghi backup history
    public BackupHistory saveAndFlush(BackupHistory backupHistory);

    // Xóa bản ghi backup history
    public void deleteById(UUID id);

    // Tim kiếm bản ghi backup history theo backupTime
    public List<BackupHistory> findByBackupTime(String backupTime);

    // Tim kiếm bản ghi backup history theo userId
    public List<BackupHistory> findByUserId(UUID userId);

    // Tim kiếm bản ghi backup history theo note
    public List<BackupHistory> findByNote(String note);

}
