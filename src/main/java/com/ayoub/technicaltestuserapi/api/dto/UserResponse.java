package com.ayoub.technicaltestuserapi.api.dto;

import com.ayoub.technicaltestuserapi.metier.model.Gender;

import java.time.LocalDate;

public record UserResponse(
        String username,
        LocalDate birthdate,
        String country,
        String phone,
        Gender gender
) {}
