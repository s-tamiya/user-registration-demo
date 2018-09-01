package com.example.registrationdemo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.registrationdemo.entity.User;

//http://pppurple.hatenablog.com/entry/2016/10/24/031642

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTests {
    
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    final private MediaType contentTypeText = new MediaType(
        MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8")
        );
    final private MediaType contentTypeJson = new MediaType(
            MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8")
            );

    @Before
    public void setup() {
      mvc = MockMvcBuilders
              .webAppContextSetup(context)
              .build();
    }
    
    @Test
    public void find() throws Exception {
        User expected = new User(1L, "test1", "pass", "test1@example.com");
        RequestBuilder builder = MockMvcRequestBuilders.get("/user/{id}", 1L)
                .accept(contentTypeJson);
        
        mvc.perform(builder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentTypeJson))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.name").value(expected.getName()))
            .andExpect(jsonPath("$.password").value(expected.getPassword()))
            .andExpect(jsonPath("$.email").value(expected.getEmail()))
            .andDo(print());
    }
}
