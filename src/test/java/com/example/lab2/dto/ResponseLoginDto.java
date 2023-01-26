package com.example.lab2.dto;

import lombok.Data;

@Data
public class ResponseLoginDto {
    private UserDto user;
    private String token;
}
