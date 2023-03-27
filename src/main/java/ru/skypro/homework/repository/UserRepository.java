package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u FROM User u
            WHERE UPPER(u.email) = UPPER(?1)
            """)
    Optional<User> findByEmail(String email);

    @Query("""
            SELECT (count(u) > 0) FROM User u WHERE UPPER(u.email) = UPPER(?1)
            """)
    boolean existsByEmail(String email);

}
