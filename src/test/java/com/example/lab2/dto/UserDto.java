package com.example.lab2.dto;

import com.example.lab2.model.Role;
import com.example.lab2.model.Status;
import lombok.Data;

import java.util.Objects;

@Data
public class UserDto {
    private Long id;
    private String login;
    private Role role;
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(login, userDto.login) && role == userDto.role && status == userDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, role, status);
    }
}
