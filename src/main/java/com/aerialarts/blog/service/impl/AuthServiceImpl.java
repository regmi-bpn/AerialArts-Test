package com.aerialarts.blog.service.impl;

import com.aerialarts.blog.controller.model.AuthRequest;
import com.aerialarts.blog.controller.model.AuthResponse;
import com.aerialarts.blog.controller.model.CurrentUserResponse;
import com.aerialarts.blog.entity.User;
import com.aerialarts.blog.exception.BadRequestException;
import com.aerialarts.blog.repository.UserRepository;
import com.aerialarts.blog.service.AuthService;
import com.aerialarts.blog.service.model.UserRequest;
import com.aerialarts.blog.service.validators.UserValidator;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final UserValidator userValidator;

    private final UserRepository userRepository;

    private static final long currentTime = System.currentTimeMillis();

    @Autowired
    public AuthServiceImpl(UserValidator userValidator, UserRepository userRepository) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
    }

    @Override
    public CurrentUserResponse getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken context) {
            return new CurrentUserResponse(context.getPrincipal().toString());
        }
        return null;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userValidator.findUserByUsername(request.getUsername());
        checkIfPasswordIsCorrect(request.getPassword(), user.getPassword());
        return new AuthResponse(buildToken(request.getUsername()).compact());
    }

    @Override
    public AuthResponse registerUser(UserRequest userRequest) {
        User user = new User();
        checkIfUsernameIsPresentOrNot(userRequest.getUsername());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder().encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user = userRepository.save(user);
        return new AuthResponse(buildToken(user.getUsername()).compact());
    }

    private JwtBuilder buildToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        Map<String, Object> claims = new HashMap<>();
        claims.put("createdAt", currentTime);
        return Jwts.builder().setSubject(username).setExpiration(new Date(currentTime + (30 * 60 * 1000))).addClaims(claims).signWith(key, SignatureAlgorithm.HS512);
    }

    private void checkIfUsernameIsPresentOrNot(String username) {
        if (userRepository.findUserByUsername(username).isPresent())
            throw new BadRequestException("A user with the username is already present.");
    }

    private void checkIfPasswordIsCorrect(String password, String savedPassword) {
        if (!passwordEncoder().matches(password, savedPassword))
            throw new BadRequestException("The password you entered is incorrect.");
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
