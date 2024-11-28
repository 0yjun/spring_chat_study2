package com.springchatstudy2.domain.auth.service;

import com.springchatstudy2.domain.auth.dto.UserDto;
import com.springchatstudy2.domain.user.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements UserDetailsService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto finduUserDto = userService.getUserByEmail(email);
        return new User(
                finduUserDto.getName(),
                finduUserDto.getPassword(),
                new ArrayList<>()
        );
    }
}
