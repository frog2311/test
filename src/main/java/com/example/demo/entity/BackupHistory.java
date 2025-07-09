package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

import java.util.UUID;

// import com.example.demo.entity.Users;
import lombok.Data;

@Entity
@Data
public class BackupHistory {
    
    @Id
    //@Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "backup_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false) 
    private String backupTime;

    @Column(name = "backup_path", length = 255, nullable = false)
    private String backupPath;

    @Column(name = "note", columnDefinition = "NVARCHAR(255)")
    private String note;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne 
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)  
    private Users user;

}
