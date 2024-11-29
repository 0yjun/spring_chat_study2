package com.springchatstudy2.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "USER 생성")
@Data
public class CreateUserRequest {
    @Schema(description = "유저 email")
    @NotNull(message="Email cannot be null")
    @NotBlank
    @Size(min = 2, message = "Email can not be 2 char..")
    private String email;

    @Schema(description = "유저명")
    @NotNull(message="name cannot be null")
    @Size(min = 2, message = "password over than 8 char..")
    private String name;


    @Schema(description = "비밀번호")
    @NotNull(message="password cannot be null")
    @NotBlank
    @Size(min = 8, message = "password can not be 8 char..")
    private String password;
}
