package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.controller.model.CurrentUserResponse;
import com.aerialarts.blog.entity.User;
import com.aerialarts.blog.exception.BadRequestException;
import com.aerialarts.blog.exception.UnauthorizedRequestException;
import com.aerialarts.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorImpl implements UserValidator {

    private final UserRepository userRepository;


    @Autowired
    public UserValidatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new BadRequestException("The provided username could not be found."));
    }

    @Override
    public User getUser() {
        return findUserByUsername(getCurrentUserName().getUsername());
    }

    @Override
    public void findBlogUpdateAccess(User author) {
        if (!author.equals(getUser()))
            throw new UnauthorizedRequestException("You are not authorized to update this blog.");
    }

    private CurrentUserResponse getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken context) {
            return new CurrentUserResponse(context.getPrincipal().toString());
        }
        throw new UnauthorizedRequestException("Not Logged In.");
    }
}
