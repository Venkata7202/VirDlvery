package com.virtusa.dlvery.common.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAssignedRoleDTO {
    private UUID assignmentId;
    private UUID userId;
    private UUID roleId;
}
