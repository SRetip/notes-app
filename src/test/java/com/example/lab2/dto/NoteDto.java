package com.example.lab2.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoteDto {
    private String topic;
    private String content;
    private Date date;

}
