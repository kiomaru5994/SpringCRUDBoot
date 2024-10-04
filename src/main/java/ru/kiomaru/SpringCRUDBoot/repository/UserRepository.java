package ru.kiomaru.SpringCRUDBoot.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kiomaru.SpringCRUDBoot.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByTelegramAccount(String telegramAccount);

    @Query("select u from User u join fetch u.roles where u.username=:username")
    User findByUsername(String username);

    @EntityGraph(attributePaths = {"roles"})
    void deleteByUsername(String username);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();

}
