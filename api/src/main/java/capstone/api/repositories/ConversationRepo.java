package capstone.api.repositories;

import capstone.api.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    List<Conversation> findAllByUser(String username);

}