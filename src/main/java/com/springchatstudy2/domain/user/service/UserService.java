package com.springchatstudy2.domain.user.service;

import com.springchatstudy2.domain.user.entity.UserEntity;
import com.springchatstudy2.domain.user.repository.UserRepository;
import com.springchatstudy2.domain.auth.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto getUserByEmail(String email){
        UserEntity userEntity = userRepository
                .findByEmail(email)
                .orElseThrow(()->{
                    //TODO CUSTOM EXCEPTION
                    throw new NoSuchElementException("일치하는 값이 없습니다");
                });
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }
}
