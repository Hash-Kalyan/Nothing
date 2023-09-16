package com.application.nothing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "last_login")
    @Temporal(TemporalType.DATE)
    private Date lastLogin;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

}


