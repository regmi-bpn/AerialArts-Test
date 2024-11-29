package com.aerialarts.blog.config;

import com.aerialarts.blog.exception.BadRequestException;
import com.aerialarts.blog.exception.UnauthorizedRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            try {
                token = token.substring(6).trim();
                SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
                Claims claims = Jwts.parser().clock(() -> new Date()).verifyWith(key).build().parseSignedClaims(token).getPayload();
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claims.getSubject(), "", new ArrayList<>()));
            } catch (JwtException | IllegalArgumentException e) {
                throw new UnauthorizedRequestException("Invalid or expired token!!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
