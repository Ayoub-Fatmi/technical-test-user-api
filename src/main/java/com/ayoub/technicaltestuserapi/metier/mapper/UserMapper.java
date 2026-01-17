package com.ayoub.technicaltestuserapi.metier.mapper;

import com.ayoub.technicaltestuserapi.api.dto.UserRequest;
import com.ayoub.technicaltestuserapi.api.dto.UserResponse;
import com.ayoub.technicaltestuserapi.metier.model.User;
import com.ayoub.technicaltestuserapi.metier.persistence.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UserMapper {

    public User toModel(UserRequest req) {

        String username = req.username().trim();

        String country = req.country() == null ? null : req.country().trim().toUpperCase(Locale.ROOT);

        String phone = (req.phone() == null || req.phone().isBlank()) ? null : req.phone().trim();

        return new User(
                username,
                req.birthdate(),
                country,
                phone,
                req.gender()
        );
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.username(),
                user.birthdate(),
                user.country(),
                user.phone(),
                user.gender()
        );
    }

    public UserResponse toResponse(UserEntity e) {
        return new UserResponse(
                e.getUsername(),
                e.getBirthdate(),
                e.getCountry(),
                e.getPhone(),
                e.getGender()
        );
    }
}
