package com.virtusa.dlvery.common.Repository;

import com.virtusa.dlvery.common.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRespository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email);



}
