package fi.academy.demo.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String user);
    boolean existsByUsername(String Username);
    boolean existsByEmail(String email);
}
