package ru.kiomaru.SpringCRUDBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kiomaru.SpringCRUDBoot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    boolean existsByTelegramAccount(String telegramAccount);
}
