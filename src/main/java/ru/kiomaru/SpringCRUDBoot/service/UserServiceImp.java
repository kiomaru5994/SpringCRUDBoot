package ru.kiomaru.SpringCRUDBoot.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kiomaru.SpringCRUDBoot.enums.Citizenship;
import ru.kiomaru.SpringCRUDBoot.enums.RoleNames;
import ru.kiomaru.SpringCRUDBoot.model.User;
import ru.kiomaru.SpringCRUDBoot.repository.UserRepository;

import java.util.*;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int id) {
        return userRepository.findById((long) id).get();
    }

    @Override
    @Transactional
    public void saveEditedUser(int id, User user) {
        User editedUser = userRepository.findById((long) id).get();
        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());
        editedUser.setUserName(user.getUsername());
        editedUser.setEmail(user.getEmail());
        userRepository.save(editedUser);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById((long) id);
    }

    public List<String> getAllCountries() {
        return Arrays.stream(Citizenship.values())
                .map(Citizenship::toString)
                .toList();
    }

    public List<String> getAllRoles() {
        return Arrays.stream(RoleNames.values())
                .map(RoleNames::toString)
                .toList();
    }

    @Transactional
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public boolean existByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Transactional
    public boolean existByTelegramAccount(String tgAccount) {
        return userRepository.existsByTelegramAccount(tgAccount);
    }
}
