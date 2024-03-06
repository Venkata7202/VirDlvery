package com.virtusa.dlvery.common.DTO;

import com.virtusa.dlvery.common.Entities.Role;
import com.virtusa.dlvery.common.Entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private List<Role> role;
}
