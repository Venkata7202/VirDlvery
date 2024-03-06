package com.virtusa.dlvery.common.Repository.Service;

import com.virtusa.dlvery.common.DTO.AddRoleRequest;
import com.virtusa.dlvery.common.DTO.UserRoleDTO;
import com.virtusa.dlvery.common.Entities.UserRole;
import com.virtusa.dlvery.common.Repository.UserRoleRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserRoleService {

    private final Logger logger = LoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRoleDTO findByRoleName(String roleName) {
        logger.info("Finding user role by name: {}", roleName);

        if (roleName == null) {
            logger.error("Role name is null. Unable to find user role.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Optional<UserRole> userRoleOptional = userRoleRepository.findByRoleName(roleName);

        if (userRoleOptional.isEmpty()) {
            logger.info("No user role found with name: {}", roleName);
            // Handle case when no user role is found, return null/empty DTO
            return null;
        }

        UserRole userRole = userRoleOptional.get();
        return DTOConversionUtil.convertToUserRoleDTO(userRole);
    }

    public List<UserRoleDTO> getAllUserRoles() {
        logger.info("Fetching all user roles");
        List<UserRole> userRoles = userRoleRepository.findAll();
        return DTOConversionUtil.convertToUserRoleDTOList(userRoles);
    }

    public UserRoleDTO createUserRole(AddRoleRequest userRoleDTO) {
        logger.info("Creating user role: {}", userRoleDTO.getRoleName());

        if (userRoleDTO.getRoleName() == null) {
            logger.error("Role name is null. Unable to create user role.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Check if the role with the same name already exists
        Optional<UserRole> existingUserRole = userRoleRepository.findByRoleName(userRoleDTO.getRoleName());

        if (existingUserRole.isPresent()) {
            logger.info("User role with name '{}' already exists. Unable to create duplicate user role.", userRoleDTO.getRoleName());
            // Handle case when the user role already exists, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        UserRole userRole = new UserRole();
        userRole.setId(UUID.randomUUID());
        userRole.setRoleName(userRoleDTO.getRoleName());

        // Save user role
        UserRole savedUserRole = userRoleRepository.save(userRole);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToUserRoleDTO(savedUserRole);
    }

    public UserRoleDTO updateUserRole(UUID roleId, UserRoleDTO updatedUserRoleDTO) {
        logger.info("Updating user role with ID: {}", roleId);

        // Check if user role with given ID exists
        Optional<UserRole> existingUserRole = userRoleRepository.findById(roleId);

        if (existingUserRole.isEmpty()) {
            logger.info("No user role found with ID: {}", roleId);
            // Handle case when no user role is found, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        UserRole updatedUserRole = DTOConversionUtil.convertToUserRole(updatedUserRoleDTO);

        // Set the ID for the existing user role
        updatedUserRole.setId(roleId);

        // Save updated user role
        UserRole savedUserRole = userRoleRepository.save(updatedUserRole);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToUserRoleDTO(savedUserRole);
    }

    public void deleteUserRole(UUID roleId) {
        logger.info("Deleting user role with ID: {}", roleId);
        userRoleRepository.deleteById(roleId);
    }
}
