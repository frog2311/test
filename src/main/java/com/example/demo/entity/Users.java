package com.example.demo.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    //@Column(name = " id", nullable = false, unique = true) 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_name", columnDefinition = "NVARCHAR(300)")   
    private String firstName;

    @Column(name = "last_name", columnDefinition = "NVARCHAR(300)")
    private String lastName;

    @Column(name = "email", length = 300, unique = true)
    private String email;

}