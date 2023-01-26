package com.example.lab2;

import com.example.lab2.dto.*;
import com.example.lab2.model.Role;
import com.example.lab2.model.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class Lab2ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private static String token;


    @Test
    @Order(1)
    void accessDeniedTest() throws Exception {

        this.mockMvc.perform(get("/notes?n=1"))
                .andExpect(status().isForbidden());
        this.mockMvc.perform(get("/notes"))
                .andExpect(status().isForbidden());
        this.mockMvc.perform(post("/notes"))
                .andExpect(status().isForbidden());
        this.mockMvc.perform(delete("/notes/5"))
                .andExpect(status().isForbidden());
        this.mockMvc.perform(put("/notes/5"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    public void incorrectUsernameAndPassword() throws Exception {
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setLogin("User1");
        authenticationRequestDto.setPassword("User1");
        String content = objectMapper.writeValueAsString(authenticationRequestDto);
        this.mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @Order(4)
    public void correctUsernameAndPassword() throws Exception {
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setLogin("User");
        authenticationRequestDto.setPassword("User");
        String content = objectMapper.writeValueAsString(authenticationRequestDto);
        String resp = this.mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        ResponseLoginDto response = objectMapper.readValue(resp, ResponseLoginDto.class);
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setLogin("User");
        userDto.setRole(Role.USER);
        userDto.setStatus(Status.ACTIVE);
        Assertions.assertEquals(userDto, response.getUser());
        token = response.getToken();
    }

    @Test
    @Order(5)
    public void shodReturnAllNotes() throws Exception {
        String expected = "{\"content\":[{\"id\":1,\"topic\":\"TopicTest\",\"content\":\"Test1\",\"date\":\"2023-01-20T10:43:01.000+00:00\"},{\"id\":2,\"topic\":\"TopicTest\",\"content\":\"Test2\",\"date\":\"2023-01-21T10:43:01.000+00:00\"},{\"id\":3,\"topic\":\"TopicTest\",\"content\":\"Test3\",\"date\":\"2023-01-23T10:43:01.000+00:00\"}],\"pageable\":{\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"offset\":0,\"pageNumber\":0,\"pageSize\":10,\"unpaged\":false,\"paged\":true},\"last\":true,\"totalElements\":3,\"totalPages\":1,\"size\":10,\"number\":0,\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"numberOfElements\":3,\"first\":true,\"empty\":false}";
        this.mockMvc
                .perform(get("/notes?n=0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{id: 1}")
                        .header("Authorization", "Bearer_".concat(token)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));

    }

    @Test
    @Order(6)
    public void shodReturnOneNote() throws Exception {
        this.mockMvc
                .perform(get("/notes/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer_".concat(token)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(7)
    public void shodReturnUpdateNote() throws Exception {
        NoteDto noteDto = new NoteDto();
        noteDto.setContent("Test4");
        noteDto.setTopic("Test4");
        noteDto.setDate(new Date());
        String json = objectMapper.writeValueAsString(noteDto);
        this.mockMvc
                .perform(put("/notes/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer_".concat(token)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(8)
    public void shodAddNote() throws Exception {
        AddNoteDtoRequest noteDto = new AddNoteDtoRequest();
        noteDto.setContent("Test4");
        noteDto.setTopic("Test4");
        noteDto.setDate(new Date());
        noteDto.setUser_id(1L);
        String json = objectMapper.writeValueAsString(noteDto);
        this.mockMvc
                .perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer_".concat(token)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(9)
    public void deleteNote() throws Exception {
        this.mockMvc
                .perform(delete("/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer_".concat(token)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(10)
    public void tryToDoSomethingStrangeNote() throws Exception {
        this.mockMvc.perform(get("/notes/8")
                        .header("Authorization", "Bearer_".concat(token)))
                .andExpect(content().string(""));
        this.mockMvc.perform(put("/notes/8")
                        .header("Authorization", "Bearer_".concat(token)))
                .andExpect(content().string(""));
    }

}
