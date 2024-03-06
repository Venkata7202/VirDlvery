package com.virtusa.dlvery.common.Controller;

import com.virtusa.dlvery.common.DTO.AddRoleRequest;
import com.virtusa.dlvery.common.DTO.UserRoleDTO;
import com.virtusa.dlvery.common.Repository.Service.UserRoleService;
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
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/{roleName}")
    public ResponseEntity<UserRoleDTO> getUserRoleByRoleName(@PathVariable String roleName) {
        logger.info("Received request to get user role by name: {}", roleName);

        UserRoleDTO userRole = userRoleService.findByRoleName(roleName);

        if (userRole == null) {
            logger.info("No user role found with name: {}", roleName);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning user role: {}", userRole.getRoleName());
        return ResponseEntity.ok(userRole);
    }

    @GetMapping
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
        logger.info("Received request to get all user roles");

        List<UserRoleDTO> userRoles = userRoleService.getAllUserRoles();

        if (userRoles.isEmpty()) {
            logger.info("No user roles found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} user roles", userRoles.size());
        return ResponseEntity.ok(userRoles);
    }

    @PostMapping
    public ResponseEntity<UserRoleDTO> createUserRole(@Validated @RequestBody AddRoleRequest userRoleDTO) {
        logger.info("Received request to create user role: {}", userRoleDTO.getRoleName());

        UserRoleDTO createdUserRole = userRoleService.createUserRole(userRoleDTO);

        if (createdUserRole == null) {
            logger.info("User role creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("User role created successfully: {}", createdUserRole.getRoleName());
        return new ResponseEntity<>(createdUserRole, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<UserRoleDTO> updateUserRole(
            @PathVariable UUID roleId,
            @Validated @RequestBody UserRoleDTO updatedUserRoleDTO) {
        logger.info("Received request to update user role with ID: {}", roleId);

        UserRoleDTO updatedUserRole = userRoleService.updateUserRole(roleId, updatedUserRoleDTO);

        if (updatedUserRole == null) {
            logger.info("User role update failed. No user role found with ID: {}", roleId);
            return ResponseEntity.notFound().build();
        }

        logger.info("User role updated successfully: {}", updatedUserRole.getRoleName());
        return ResponseEntity.ok(updatedUserRole);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable UUID roleId) {
        logger.info("Deleting user role with ID: {}", roleId);

        userRoleService.deleteUserRole(roleId);

        logger.info("User role deleted successfully with ID: {}", roleId);
        return ResponseEntity.noContent().build();
    }
}
