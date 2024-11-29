package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.entity.User;

public interface UserValidator {

    User findUserByUsername(String username);

    User getUser();

    void findBlogUpdateAccess(User author);
}
