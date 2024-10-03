package ru.kiomaru.SpringCRUDBoot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kiomaru.SpringCRUDBoot.enums.RoleNames;
import ru.kiomaru.SpringCRUDBoot.model.Role;
import ru.kiomaru.SpringCRUDBoot.repository.RoleRepository;

@Component
@Order(1)
public class RoleDataInitializer implements CommandLineRunner {
    RoleRepository roleRepository;

    @Autowired
    public RoleDataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Transactional
    @Override
    public void run(String... args) {
        if (!roleRepository.existsByRoleName("ADMIN")) {
            Role admin = new Role();
            admin.setRoleName(RoleNames.ADMIN.name());
            admin.setDescription("This if Admin role");
            roleRepository.save(admin);
        }
        if (!roleRepository.existsByRoleName("USER")) {
            Role user = new Role();
            user.setRoleName(RoleNames.USER.name());
            user.setDescription("This if User role");
            roleRepository.save(user);
        }
    }
}
