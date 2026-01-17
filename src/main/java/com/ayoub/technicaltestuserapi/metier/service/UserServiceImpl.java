package com.ayoub.technicaltestuserapi.metier.service;

import com.ayoub.technicaltestuserapi.api.dto.UserRequest;
import com.ayoub.technicaltestuserapi.api.dto.UserResponse;
import com.ayoub.technicaltestuserapi.metier.exceptions.EligibilityException;
import com.ayoub.technicaltestuserapi.metier.exceptions.UserAlreadyExistsException;
import com.ayoub.technicaltestuserapi.metier.exceptions.UserNotFoundException;
import com.ayoub.technicaltestuserapi.metier.mapper.UserMapper;
import com.ayoub.technicaltestuserapi.metier.persistence.UserEntity;
import com.ayoub.technicaltestuserapi.metier.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest req) {
        String username = req.username().trim();

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username '" + username + "' already exists");
        }

        validateEligibility(req);

        UserEntity saved = userRepository.save(mapper.toEntity(mapper.toModel(req)));
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getDetails(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User '" + username + "' not found"));

        return mapper.toResponse(user);
    }

    private void validateEligibility(UserRequest req) {
        String country = req.country() == null ? "" : req.country().trim().toUpperCase(Locale.ROOT);
        if (!country.equals("FR") && !country.equals("FRANCE")) {
            throw new EligibilityException("Only French residents are allowed to create an account");
        }

        int age = Period.between(req.birthdate(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new EligibilityException("Only adults are allowed to create an account");
        }
    }
}
