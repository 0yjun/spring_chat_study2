package com.springchatstudy2.domain.user.controller;

import com.springchatstudy2.config.security.JWTProvider;
import com.springchatstudy2.domain.user.dto.CreateUserRequest;
import com.springchatstudy2.domain.user.dto.UserDto;
import com.springchatstudy2.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AUTH API", description = "V1 Auth API")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JWTProvider jwtProvider;

    @Operation(
            summary = "새로운 유저를 생성",
            description = "새로운 유저 생성"
    )
    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUserRequest createUserRequest){
        UserDto newUser = userService.createUser(createUserRequest);
        String token = jwtProvider.createToken(newUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
