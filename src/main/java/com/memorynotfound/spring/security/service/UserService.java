package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    void updatePassword(String password, Long userId);
}
