package capstone.api.repositories;

import capstone.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    User findAllByEmail(String username);
}