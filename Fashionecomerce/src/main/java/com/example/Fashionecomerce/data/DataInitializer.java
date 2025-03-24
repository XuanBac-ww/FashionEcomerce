package com.example.Fashionecomerce.data;

import com.example.Fashionecomerce.model.Role;
import com.example.Fashionecomerce.model.User;
import com.example.Fashionecomerce.repository.RoleRepository;
import com.example.Fashionecomerce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_USER" , "ROLE_ADMIN", "ROLE_CUSTOMER");
        createDefaultRoles(defaultRoles);
        createDefaultAdminIfNotExits();
    }

    private void createDefaultRoles(Set<String> roles){
        roles.stream()
                .filter(role -> Optional.ofNullable(roleRepository.findByName(role))
                        .isEmpty()).map(Role::new).forEach(roleRepository::save);
    }

    private void createDefaultAdminIfNotExits(){
        Role adminRole = Optional.ofNullable(roleRepository.findByName("ROLE_ADMIN"))
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        for (int i = 1; i<=3; i++){
            String defaultEmail = "admin"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Shop User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
        }
    }
}
