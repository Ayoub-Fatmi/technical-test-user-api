package com.ayoub.technicaltestuserapi.api;

import com.ayoub.technicaltestuserapi.api.dto.UserRequest;
import com.ayoub.technicaltestuserapi.metier.model.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void register_then_getDetails_shouldReturn201_then_200() throws Exception {
        UserRequest req = new UserRequest(
                "clement",
                LocalDate.parse("1995-03-10"),
                "France",
                "06 12 34 56 78",
                Gender.OTHER
        );

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("clement"))
                .andExpect(jsonPath("$.country").value("FRANCE"));

        mvc.perform(get("/api/users/clement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("clement"));
    }

    @Test
    void register_shouldReturn400_String_whenNotFrenchResident() throws Exception {
        UserRequest req = new UserRequest(
                "Gerd",
                LocalDate.parse("1990-01-01"),
                "Germany",
                null,
                null
        );

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_shouldReturn400_String_whenMinor() throws Exception {
        UserRequest req = new UserRequest(
                "charlie",
                LocalDate.parse("2010-01-01"),
                "France",
                null,
                null
        );

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getDetails_shouldReturn404_String_whenNotFound() throws Exception {
        mvc.perform(get("/api/users/unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void register_shouldReturn409_String_whenDuplicateUsername() throws Exception {
        UserRequest req = new UserRequest(
                "celia",
                LocalDate.parse("1990-01-01"),
                "France",
                null,
                null
        );

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    void register_shouldReturn400_String_whenValidationFails() throws Exception {
        String invalidJson = """
                {
                  "username": "",
                  "birthdate": "1990-01-01",
                  "country": "France"
                }
                """;

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("username")));
    }
}
