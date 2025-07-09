package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    // Thêm người dùng mới
    public Users save(Users user);

    // Check emnail đã tồn tại hay chưa
    public boolean existsByEmail(String email);

    // check id đã tồn tại hay chưa
    public boolean existsById(UUID id);

    // Tìm kiếm người dùng theo email
    public Users findByEmail(String email);
}
