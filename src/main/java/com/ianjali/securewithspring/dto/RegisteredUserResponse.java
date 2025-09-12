package com.ianjali.securewithspring.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegisteredUserResponse {

    private Long userId;
    private String userName;
    private String email;
    private Set<String> roles;
    private String message;
}
