package com.springchatstudy2.domain.auth.service;

import com.springchatstudy2.domain.auth.dto.AuthDto;
import com.springchatstudy2.domain.user.dto.UserDto;
import com.springchatstudy2.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements UserDetailsService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto finduUserDto = userService.getUserByEmail(email);
        return AuthDto.builder()
                .id(finduUserDto.getId())
                .email(finduUserDto.getEmail())
                .name(finduUserDto.getName())
                .password(finduUserDto.getPassword())
                .build();
    }
}
