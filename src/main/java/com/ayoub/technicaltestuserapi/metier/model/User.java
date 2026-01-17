package com.ayoub.technicaltestuserapi.metier.model;

import java.time.LocalDate;

public record User(
        String username,
        LocalDate birthdate,
        String country,
        String phone,
        Gender gender
) {}