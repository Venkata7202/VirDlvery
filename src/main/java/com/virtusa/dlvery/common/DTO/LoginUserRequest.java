package com.virtusa.dlvery.common.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class LoginUserRequest {

    @NotBlank(message = "User name is required parameter.")
    private String userName;

    @NotBlank(message = "Password is required parameter.")
    private String password;

    private int isSocialRegister;

    @Override
    public String toString() {
        return "LoginUserRequest{" +
                "userName='" + userName + '\'' +
                ", isSocialRegister=" + isSocialRegister +
                '}';
    }
}
