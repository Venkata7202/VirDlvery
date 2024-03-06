package com.virtusa.dlvery.common.Repository.Service;

import com.virtusa.dlvery.common.DTO.UserAssignedRoleDTO;
import com.virtusa.dlvery.common.Entities.UserAssignedRole;
import com.virtusa.dlvery.common.Repository.UserAssignedRoleRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAssignedRoleService {

    private final Logger logger = LoggerFactory.getLogger(UserAssignedRoleService.class);

    @Autowired
    private UserAssignedRoleRepository userAssignedRoleRepository;


    public List<UserAssignedRoleDTO> getAllUserAssignedRoles() {
        logger.info("Fetching all user assigned roles");
        List<UserAssignedRole> userAssignedRoles = userAssignedRoleRepository.findAll();
        return DTOConversionUtil.convertToUserAssignedRoleDTOList(userAssignedRoles);
    }

    public UserAssignedRoleDTO getUserAssignedRoleById(UUID assignmentId) {
        logger.info("Fetching user assigned role by ID: {}", assignmentId);
        Optional<UserAssignedRole> userAssignedRoleOptional = userAssignedRoleRepository.findById(assignmentId);

        if (userAssignedRoleOptional.isPresent()) {
            return DTOConversionUtil.convertToUserAssignedRoleDTO(userAssignedRoleOptional.get());
        } else {
            logger.info("No user assigned role found with ID: {}", assignmentId);
            return null;
        }
    }

    public UserAssignedRoleDTO createUserAssignedRole(UserAssignedRoleDTO userAssignedRoleDTO) {
        logger.info("Creating user assigned role");

        // Check if the assignmentId is provided in DTO, if so, it might be an update request
        if (userAssignedRoleDTO.getAssignmentId() != null) {
            logger.error("Assignment ID should not be provided for a new user assigned role creation.");
            return null;
        }

        // Convert DTO to entity
        UserAssignedRole userAssignedRole = DTOConversionUtil.convertToUserAssignedRole(userAssignedRoleDTO);

        // Save user assigned role
        UserAssignedRole savedUserAssignedRole = userAssignedRoleRepository.save(userAssignedRole);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToUserAssignedRoleDTO(savedUserAssignedRole);
    }

    public UserAssignedRoleDTO updateUserAssignedRole(UUID assignmentId, UserAssignedRoleDTO updatedUserAssignedRoleDTO) {
        logger.info("Updating user assigned role with ID: {}", assignmentId);

        // Check if user assigned role with given ID exists
        if (!userAssignedRoleRepository.existsById(assignmentId)) {
            logger.info("No user assigned role found with ID: {}", assignmentId);
            // Handle case when no user assigned role is found, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        UserAssignedRole updatedUserAssignedRole = DTOConversionUtil.convertToUserAssignedRole(updatedUserAssignedRoleDTO);

        // Set the ID for the existing user assigned role
        updatedUserAssignedRole.setAssignmentId(assignmentId);

        // Save updated user assigned role
        UserAssignedRole savedUserAssignedRole = userAssignedRoleRepository.save(updatedUserAssignedRole);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToUserAssignedRoleDTO(savedUserAssignedRole);
    }

    public void deleteUserAssignedRole(UUID assignmentId) {
        logger.info("Deleting user assigned role with ID: {}", assignmentId);
        userAssignedRoleRepository.deleteById(assignmentId);
    }
}

