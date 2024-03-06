package com.virtusa.dlvery.common.DTO;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDTO {

    private UUID roleId;
    private String roleName;

}

