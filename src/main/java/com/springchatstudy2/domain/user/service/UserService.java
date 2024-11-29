package com.springchatstudy2.domain.user.service;

import com.springchatstudy2.domain.auth.dto.AuthDto;
import com.springchatstudy2.domain.user.dto.CreateUserRequest;
import com.springchatstudy2.domain.user.dto.UserDto;
import com.springchatstudy2.domain.user.entity.UserEntity;
import com.springchatstudy2.domain.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * 주어진 이메일을 기반으로 사용자 정보를 조회.
     * @param email 조회할 사용자의 이메일
     * @return 사용자 정보를 담은 `UserDto` 객체
     * @throws NoSuchElementException 이메일에 해당하는 사용자가 존재하지 않으면 발생
     */
    public UserDto getUserByEmail(String email){
        UserEntity userEntity = userRepository
                .findByEmail(email)
                .orElseThrow(()->{
                    //TODO CUSTOM EXCEPTION
                    throw new NoSuchElementException("일치하는 값이 없습니다 1");
                });
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    /**
     * 주어진 이메일을 기반으로 사용자 정보를 조회.
     * @param createUserRequest USER를 생성할 정보를 담은 객체
     * @return 사용자 정보를 담은 `UserDto` 객체
     * @throws
     */
    @Transactional
    public UserDto createUser(CreateUserRequest createUserRequest){
        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            //TODO CUSTOM EXCEPTION 구현
            throw new RuntimeException();
        }
        UserEntity newUser = UserEntity.builder()
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .name(createUserRequest.getName())
                .build();
        userRepository.save(newUser);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(newUser, UserDto.class);
    }
}
