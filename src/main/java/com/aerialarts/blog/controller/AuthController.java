package com.aerialarts.blog.controller;

import com.aerialarts.blog.controller.model.AuthRequest;
import com.aerialarts.blog.controller.model.AuthResponse;
import com.aerialarts.blog.controller.model.CurrentUserResponse;
import com.aerialarts.blog.service.AuthService;
import com.aerialarts.blog.service.model.UserRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/v1/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/v1/user/current")
    public CurrentUserResponse getCurrentUser() {
        return authService.getCurrentUserName();

    }

    @PostMapping("/v1/register")
    public AuthResponse registerUser(@RequestBody UserRequest userRequest) {
        return authService.registerUser(userRequest);
    }

}
