package com.virtusa.dlvery.common.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID userId;
    private String fullName;
    private String userName;
    private String password;
    private List<String> roles;
    private Integer isSocialRegister;
    private Integer otp;
    private Integer isAccountVerify;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

