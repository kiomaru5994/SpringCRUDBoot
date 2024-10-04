package ru.kiomaru.SpringCRUDBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
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

    @Transactional(readOnly = true)
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void saveEditedUser(String username, User user) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setMiddleName(user.getMiddleName());
            existingUser.setGender(user.getGender());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setAddress(user.getAddress());
            existingUser.setCitizenship(user.getCitizenship());
            existingUser.setTelegramAccount(user.getTelegramAccount());
            existingUser.setUsername(user.getUsername());
            existingUser.setRoles(user.getRoles());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(existingUser);
        }
    }

    @Override
    @Transactional
    public void delete(String username) {
        userRepository.deleteByUsername(username);
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

    @Transactional(readOnly = true)
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existByUserName(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Transactional(readOnly = true)
    public boolean existByTelegramAccount(String tgAccount) {
        return userRepository.existsByTelegramAccount(tgAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return userRepository.findByUsername(currentUserName);
    }
}
