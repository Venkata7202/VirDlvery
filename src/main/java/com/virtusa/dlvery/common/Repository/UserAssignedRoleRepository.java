package com.virtusa.dlvery.common.Repository;

import com.virtusa.dlvery.common.Entities.UserAssignedRole;
import com.virtusa.dlvery.common.Entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserAssignedRoleRepository extends JpaRepository<UserAssignedRole, UUID> {

    UserRole findByUserUserId(UUID userId);
    List<UserAssignedRole> findAllByUserUserId(UUID userId);
}



