package com.UniTech.UniTechTest.dto;

import com.UniTech.UniTechTest.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterRequest {
    private String pin;
    private String password;
    private Role role;
}