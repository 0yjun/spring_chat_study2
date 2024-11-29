package com.springchatstudy2.domain.user.dto;

import com.springchatstudy2.domain.user.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

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