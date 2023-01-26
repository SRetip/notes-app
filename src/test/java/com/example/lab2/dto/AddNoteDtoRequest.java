package com.example.lab2.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddNoteDtoRequest {
    private Long user_id;
    private String topic;
    private String content;
    private Date date;

}
