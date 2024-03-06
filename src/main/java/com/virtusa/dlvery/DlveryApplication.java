package com.virtusa.dlvery;

import com.virtusa.dlvery.common.Entities.Role;
import com.virtusa.dlvery.common.Repository.Service.AuthenticationService;
import com.virtusa.dlvery.common.Repository.Service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.virtusa.dlvery.common.DTO.AddRoleRequest;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class
DlveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DlveryApplication.class, args);
	}

    @Bean
    CommandLineRunner run(@Autowired UserRoleService userRoleService, @Autowired AuthenticationService authenticationService) {
        return args -> {
            userRoleService.createUserRole(new AddRoleRequest("USER"));
			userRoleService.createUserRole(new AddRoleRequest("INVENTORY"));
			userRoleService.createUserRole(new AddRoleRequest("DELIVERY"));

            authenticationService.register(new com.virtusa.dlvery.common.DTO.RegisterRequest("admin", "admin", "admin@gmail.com", "test@123", Arrays.asList(Role.USER, Role.DELIVERY, Role.INVENTORY)));
        };
    }
}


