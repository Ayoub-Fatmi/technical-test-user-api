package com.ayoub.technicaltestuserapi.api.controller;

import com.ayoub.technicaltestuserapi.api.dto.UserRequest;
import com.ayoub.technicaltestuserapi.api.dto.UserResponse;
import com.ayoub.technicaltestuserapi.metier.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest req) {
        UserResponse created = userService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> details(@PathVariable String username) {
        return ResponseEntity.ok(userService.getDetails(username));
    }
}