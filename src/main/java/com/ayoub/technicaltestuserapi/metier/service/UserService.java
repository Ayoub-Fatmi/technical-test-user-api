package com.ayoub.technicaltestuserapi.metier.service;

import com.ayoub.technicaltestuserapi.api.dto.UserRequest;
import com.ayoub.technicaltestuserapi.api.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRequest req);
    UserResponse getDetails(String username);
}
