package com.microservice.springsecurityoauth2mongodb.service;


import com.microservice.springsecurityoauth2mongodb.document.User;

import java.util.Optional;

/**
 * <h2>UserService</h2>
 *
 * @author aek
 * <p>
 * Description:
 */
public interface UserService {

    /**
     * @param login username or email
     * @return Optional User
     */
    Optional<User> getUserByUsername(String login);



}
