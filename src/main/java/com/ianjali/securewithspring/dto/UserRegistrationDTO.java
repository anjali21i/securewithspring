package com.ianjali.securewithspring.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String userName;
    private String password;
    private String email;
    private String roleName;
}
