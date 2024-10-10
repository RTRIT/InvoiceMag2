package com.example.invoiceProject.Config;

import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ApplicationInitConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        PrivilegeRepository privilegeRepository) {
        log.info("Initializing Application");
        //Initialize privileges CRUD Invoice
        return args -> {
            if (privilegeRepository.findAll().isEmpty()) {
                privilegeRepository.save(Privilege.builder()
                        .privilegeName("CREATE_INVOICE")
                        .privilegeDesc("")
                        .build());
                privilegeRepository.save(Privilege.builder()
                        .privilegeName("VIEW_INVOICE")
                        .privilegeDesc("")
                        .build());
                privilegeRepository.save(Privilege.builder()
                        .privilegeName("UPDATE_INVOICE")
                        .privilegeDesc("")
                        .build());
                privilegeRepository.save(Privilege.builder()
                        .privilegeName("DELETE_INVOICE")
                        .privilegeDesc("")
                        .build());
            }



            // Initialize ADMIN role if not exist
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                List<Privilege> privilegeList = privilegeRepository.findAll();
                roleRepository.save(Role.builder()
                        .roleName("ADMIN")
                        .privileges(privilegeList)
                        .build());

                roleRepository.save(Role.builder()
                        .roleName("USER")
                        .privileges(privilegeList)
                        .build());


                List<Role> roles = roleRepository.findAll();

                User user = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(roles)
                        .firstName("admin")
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };


    }
}
