package com.ayoub.technicaltestuserapi.api.dto;

import com.ayoub.technicaltestuserapi.metier.model.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequest(
        @NotBlank
        @Size(min = 3, max = 30)
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "username must contain only letters, digits, dot, underscore or dash")
        String username,

        @NotNull
        @Past(message = "birthdate must be in the past")
        LocalDate birthdate,

        @NotBlank
        @Size(min = 2, max = 56)
        String country,

        @Pattern(
                regexp = "^$|\\+?[0-9 ]{6,20}$",
                message = "phone must contain only digits/spaces and may start with +"
        )
        String phone,

        Gender gender
) {}
