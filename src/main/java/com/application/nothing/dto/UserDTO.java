package com.application.nothing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private Date createdAt;
    private Date lastLogin;

}

