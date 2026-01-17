package com.ayoub.technicaltestuserapi.metier.service;

import com.ayoub.technicaltestuserapi.api.dto.UserRequest;
import com.ayoub.technicaltestuserapi.metier.exceptions.EligibilityException;
import com.ayoub.technicaltestuserapi.metier.exceptions.UserAlreadyExistsException;
import com.ayoub.technicaltestuserapi.metier.mapper.UserMapper;
import com.ayoub.technicaltestuserapi.metier.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repo;
    private UserService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(UserRepository.class);
        UserMapper mapper = new UserMapper();
        service = new UserServiceImpl(repo, mapper);
    }

    @Test
    void register_shouldThrow_whenUsernameExists() {
        when(repo.existsByUsername("john")).thenReturn(true);

        UserRequest req = new UserRequest(
                "john",
                LocalDate.parse("1990-01-01"),
                "FRANCE",
                null,
                null
        );

        assertThrows(UserAlreadyExistsException.class, () -> service.register(req));
        verify(repo, never()).save(any());
    }

    @Test
    void register_shouldThrow_whenNotFrenchResident() {
        when(repo.existsByUsername("john")).thenReturn(false);

        UserRequest req = new UserRequest(
                "john",
                LocalDate.parse("1990-01-01"),
                "DE",
                null,
                null
        );

        assertThrows(EligibilityException.class, () -> service.register(req));
        verify(repo, never()).save(any());
    }

    @Test
    void register_shouldThrow_whenMinor() {
        when(repo.existsByUsername("john")).thenReturn(false);

        UserRequest req = new UserRequest(
                "john",
                LocalDate.parse("2010-01-01"),
                "FRANCE",
                null,
                null
        );

        assertThrows(EligibilityException.class, () -> service.register(req));
        verify(repo, never()).save(any());
    }
}
