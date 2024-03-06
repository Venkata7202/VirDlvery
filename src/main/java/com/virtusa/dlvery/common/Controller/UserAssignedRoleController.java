package com.virtusa.dlvery.common.Controller;

import com.virtusa.dlvery.common.DTO.UserAssignedRoleDTO;
import com.virtusa.dlvery.common.Repository.Service.UserAssignedRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-assigned-roles")
public class UserAssignedRoleController {

    private final Logger logger = LoggerFactory.getLogger(UserAssignedRoleController.class);

    @Autowired
    private UserAssignedRoleService userAssignedRoleService;

    @GetMapping
    public ResponseEntity<List<UserAssignedRoleDTO>> getAllUserAssignedRoles() {
        logger.info("Received request to get all user assigned roles");

        List<UserAssignedRoleDTO> userAssignedRoles = userAssignedRoleService.getAllUserAssignedRoles();

        if (userAssignedRoles.isEmpty()) {
            logger.info("No user assigned roles found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} user assigned roles", userAssignedRoles.size());
        return ResponseEntity.ok(userAssignedRoles);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<UserAssignedRoleDTO> getUserAssignedRoleById(@PathVariable UUID assignmentId) {
        logger.info("Received request to get user assigned role by ID: {}", assignmentId);

        UserAssignedRoleDTO userAssignedRole = userAssignedRoleService.getUserAssignedRoleById(assignmentId);

        if (userAssignedRole == null) {
            logger.info("No user assigned role found with ID: {}", assignmentId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning user assigned role: {}", userAssignedRole.getAssignmentId());
        return ResponseEntity.ok(userAssignedRole);
    }

    @PostMapping
    public ResponseEntity<UserAssignedRoleDTO> createUserAssignedRole(@Validated @RequestBody UserAssignedRoleDTO userAssignedRoleDTO) {
        logger.info("Received request to create user assigned role");

        UserAssignedRoleDTO createdUserAssignedRole = userAssignedRoleService.createUserAssignedRole(userAssignedRoleDTO);

        if (createdUserAssignedRole == null) {
            logger.info("User assigned role creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("User assigned role created successfully: {}", createdUserAssignedRole.getAssignmentId());
        return new ResponseEntity<>(createdUserAssignedRole, HttpStatus.CREATED);
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<UserAssignedRoleDTO> updateUserAssignedRole(
            @PathVariable UUID assignmentId,
            @Validated @RequestBody UserAssignedRoleDTO updatedUserAssignedRoleDTO) {
        logger.info("Received request to update user assigned role with ID: {}", assignmentId);

        UserAssignedRoleDTO updatedUserAssignedRole = userAssignedRoleService.updateUserAssignedRole(assignmentId, updatedUserAssignedRoleDTO);

        if (updatedUserAssignedRole == null) {
            logger.info("User assigned role update failed. No user assigned role found with ID: {}", assignmentId);
            return ResponseEntity.notFound().build();
        }

        logger.info("User assigned role updated successfully: {}", updatedUserAssignedRole.getAssignmentId());
        return ResponseEntity.ok(updatedUserAssignedRole);
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> deleteUserAssignedRole(@PathVariable UUID assignmentId) {
        logger.info("Deleting user assigned role with ID: {}", assignmentId);

        userAssignedRoleService.deleteUserAssignedRole(assignmentId);

        logger.info("User assigned role deleted successfully with ID: {}", assignmentId);
        return ResponseEntity.noContent().build();
    }
}

