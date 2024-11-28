package com.springchatstudy2.domain.auth.dto;

import com.springchatstudy2.domain.user.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link UserEntity}
 */
@Data
public class UserDto implements Serializable {
    Long id;
    String name;
    String email;
    String password;
    Timestamp created_at;
}