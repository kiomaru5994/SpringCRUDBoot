package ru.kiomaru.SpringCRUDBoot.service;

import ru.kiomaru.SpringCRUDBoot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void save(User user);
    User getUser(String username);
    void saveEditedUser(String username, User user);
    void delete(String username);
    User getUserFromSecurityContext();
}
