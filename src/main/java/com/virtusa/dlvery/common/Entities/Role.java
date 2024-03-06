package com.virtusa.dlvery.common.Entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.virtusa.dlvery.common.Entities.Permission.*;


@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  INVENTORY(
          Set.of(
                  INVENTORY_READ,
                  INVENTORY_UPDATE,
                  INVENTORY_DELETE,
                  INVENTORY_CREATE
          )
  ),
  DELIVERY(
          Set.of(
                  DELIVERY_READ,
                  DELIVERY_UPDATE,
                  DELIVERY_DELETE,
                  DELIVERY_CREATE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
