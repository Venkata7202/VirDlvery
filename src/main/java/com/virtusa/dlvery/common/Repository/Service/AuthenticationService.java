package com.virtusa.dlvery.common.Repository.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtusa.dlvery.common.Entities.*;
import com.virtusa.dlvery.common.Repository.TokenRepository;
import com.virtusa.dlvery.common.Repository.AppUserRespository;
import com.virtusa.dlvery.common.DTO.AuthenticationRequest;
import com.virtusa.dlvery.common.DTO.AuthenticationResponse;
import com.virtusa.dlvery.common.DTO.RegisterRequest;
import com.virtusa.dlvery.common.Repository.UserAssignedRoleRepository;
import com.virtusa.dlvery.common.Repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  @Autowired
  private final AppUserRespository repository;
  @Autowired
  private final TokenRepository tokenRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final AuthenticationManager authenticationManager;

  @Autowired
  private UserAssignedRoleRepository userAssignedRoleRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;


  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))

        .build();
    var savedUser = repository.save(user);


    if (!request.getRole().isEmpty()) {
      request.getRole().forEach(role ->
              userRoleRepository.findByRoleName(role.toString())
                      .ifPresent(userRole -> {
                        UserAssignedRole userAssignedRole = new UserAssignedRole();
                        userAssignedRole.setAssignmentId(UUID.randomUUID());
                        userAssignedRole.setRole(userRole);
                        userAssignedRole.setUser(user);

                        userAssignedRoleRepository.save(userAssignedRole);
                      })
      );
    }
    else{
      UserAssignedRole userAssignedRole = new UserAssignedRole();
      userAssignedRole.setAssignmentId(UUID.randomUUID());
      userAssignedRole.setRole(userRoleRepository.findByRoleName("USER").get());
      userAssignedRole.setUser(user);
    }


    user.setRoles(mapRoleNamesToUserRoles(request.getRole()));

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
      var user = repository.findByEmail(request.getEmail())
              .orElseThrow();
      List<UserAssignedRole> allByUserUserId = userAssignedRoleRepository.findAllByUserUserId(user.getUserId());
      List<UserRole> userRoles = allByUserUserId.stream()
              .map(UserAssignedRole::getRole)
              .collect(Collectors.toList());

      user.setRoles(userRoles);

      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);
      return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }


  private List<UserRole> mapRoleNamesToUserRoles(List<Role> roleNames) {
    return roleNames.stream()
            .map(this::findUserRoleByRoleName)
            .collect(Collectors.toList());
  }

  private UserRole findUserRoleByRoleName(Role roleName) {
    try {
      return userRoleRepository.findByRoleName(roleName.toString())
              .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));
    } catch (RoleNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
