package com.example.lab2.dto;

import com.example.lab2.model.Role;
import com.example.lab2.model.Status;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private Role role;
    private Status status;
}
