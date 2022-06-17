package capstone.chatbot.repositories;

import capstone.chatbot.entities.User;
//import capstone.chatbot.entities.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    User findAllByEmail(String username);
}