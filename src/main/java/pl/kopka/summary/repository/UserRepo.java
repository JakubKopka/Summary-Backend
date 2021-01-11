package pl.kopka.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kopka.summary.domain.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
