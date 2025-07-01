package org.top.countrydirectoryapp.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStatusRunning() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Server is running"))
                .andExpect(jsonPath("$.host").exists())
                .andExpect(jsonPath("$.protocol").exists());
    }

    @Test
    public void testPing() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Pong"));
    }
}