package ru.kiomaru.SpringCRUDBoot.service;

import org.springframework.stereotype.Service;
import ru.kiomaru.SpringCRUDBoot.model.User;
import ru.kiomaru.SpringCRUDBoot.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById((long) id).get();
    }

    @Override
    public void saveEditedUser(int id, User user) {
        User editedUser = userRepository.findById((long) id).get();
        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());
        editedUser.setUserName(user.getUserName());
        editedUser.setEmail(user.getEmail());
        userRepository.save(editedUser);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById((long) id);
    }
}
