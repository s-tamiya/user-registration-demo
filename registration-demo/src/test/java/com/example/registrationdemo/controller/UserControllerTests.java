package com.example.registrationdemo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.registrationdemo.entity.User;
import com.example.registrationdemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    final private MediaType contentTypeJson = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
            );

    final private MediaType contentTypeTextPlain = new MediaType(
            MediaType.TEXT_PLAIN.getType(),
            MediaType.TEXT_PLAIN.getSubtype(),
            Charset.forName("utf8")
            );

    @Test
    public void find() throws Exception {
        User expected = new User(1L, "test_user", "pass", "test1@example.com");
        String expectedJson = objectMapper.writeValueAsString(expected);
        when(userService.findUserById(expected.getUserId()))
            .thenReturn(Optional.ofNullable(expected));

        RequestBuilder builder = MockMvcRequestBuilders.get("/user/{id}", 1L)
                .accept(contentTypeJson);

        MvcResult result = mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.password").value(expected.getPassword()))
                .andExpect(jsonPath("$.email").value(expected.getEmail()))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    public void find_NotFind() throws Exception{
        Long id = 1L;
        when(userService.findUserById(id)).thenReturn(Optional.empty());

        RequestBuilder builder = MockMvcRequestBuilders.get("/user/{id}", 1L)
                   .accept(contentTypeJson);

        mvc.perform(builder)
            .andExpect(status().isNotFound())
            .andDo(print())
            .andReturn();
    }

    @Test
    public void update() throws Exception {
        User expected = new User(1L, "test_user", "pass", "test1@example.com");
        String expectedJson = objectMapper.writeValueAsString(expected);
        when(userService.findUserById(1L)).thenReturn(Optional.ofNullable(expected));

        RequestBuilder builder = MockMvcRequestBuilders.put("/user")
                .contentType(contentTypeJson)
                .content(expectedJson)
                .accept(contentTypeJson);

        mvc.perform(builder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentTypeJson))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.name").value(expected.getName()))
            .andExpect(jsonPath("$.email").value(expected.getEmail()))
            .andExpect(jsonPath("$.password").value(expected.getPassword()))
            .andDo(print());

        verify(userService, times(1)).updateUser(expected);
    }

    @Test
    public void remove() throws Exception {
        Long id = 1L;
        RequestBuilder builder = MockMvcRequestBuilders.delete("/user/{id}", id)
                .contentType(contentTypeTextPlain);

        mvc.perform(builder)
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    public void register() throws Exception {
        User expected = new User(1L, "test_user", "pass", "test1@example.com");
        String expectedJson = objectMapper.writeValueAsString(expected);

        User content = new User(null, "test_user", "pass", "test1@example.com");
        when(userService.findUserByEmail(content.getEmail()))
            .thenReturn(Optional.ofNullable(expected));

        String contentJson = objectMapper.writeValueAsString(content);
        RequestBuilder builder = MockMvcRequestBuilders.post("/user/register")
                .contentType(contentTypeJson)
                .content(contentJson)
                .accept(contentTypeJson);

        mvc.perform(builder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentTypeJson))
            .andExpect(content().json(expectedJson))
            .andDo(print());

        verify(userService, times(1)).createUser(content);
    }

}
