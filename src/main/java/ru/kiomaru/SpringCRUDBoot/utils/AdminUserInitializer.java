package ru.kiomaru.SpringCRUDBoot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kiomaru.SpringCRUDBoot.enums.Citizenship;
import ru.kiomaru.SpringCRUDBoot.enums.RoleNames;
import ru.kiomaru.SpringCRUDBoot.model.User;
import ru.kiomaru.SpringCRUDBoot.repository.RoleRepository;
import ru.kiomaru.SpringCRUDBoot.repository.UserRepository;

import java.util.Collections;


@Component
@Order(2)
public class AdminUserInitializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public AdminUserInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setAddress("Россия, Москва, Московская, 12");
            user.setCitizenship(Citizenship.RUSSIA.toString());
            user.setEmail("admin@admin.com");
            user.setFirstName("Admin");
            user.setGender("male");
            user.setLastName("Admin");
            user.setMiddleName("Admin");
            user.setPassword(passwordEncoder.encode("administrator"));
            user.setPhoneNumber("+79999999999");
            user.setTelegramAccount("@Admin");
            user.setUsername("Admin");
            user.setRoles(Collections.singletonList(roleRepository.findByRoleName(RoleNames.ADMIN.name())));
            userRepository.save(user);
            System.out.println("////////////////////////////////////////////////////////////////////////////");
            System.out.println("Класс AdminUserInitializer создан для теста, если таблица пользователей в БД пустая - создается один АДМИНИСТРАТОР.");
            System.out.println("Логин по умолчанию - Admin, пароль - administrator");
            System.out.println("////////////////////////////////////////////////////////////////////////////");
        }
    }
}
