package com.aerialarts.blog.service;

import com.aerialarts.blog.controller.model.AuthRequest;
import com.aerialarts.blog.controller.model.AuthResponse;
import com.aerialarts.blog.controller.model.CurrentUserResponse;
import com.aerialarts.blog.service.model.UserRequest;

public interface AuthService {

    CurrentUserResponse getCurrentUserName();

    AuthResponse login(AuthRequest request);

    AuthResponse registerUser(UserRequest userRequest);
}
