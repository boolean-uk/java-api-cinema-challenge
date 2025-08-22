package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.models.ERole;
import com.booleanuk.api.cinema.models.Role;
import com.booleanuk.api.cinema.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        if (this.roleRepository.findByName(ERole.ROLE_CUSTOMER).isEmpty()) {
            this.roleRepository.save(new Role(ERole.ROLE_CUSTOMER));
        }

        if (this.roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            this.roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }

        if (this.roleRepository.findByName(ERole.ROLE_TICKETMASTER).isEmpty()) {
            this.roleRepository.save(new Role(ERole.ROLE_TICKETMASTER));
        }
    }
}
