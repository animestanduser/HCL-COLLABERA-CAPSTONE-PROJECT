package capstone.chatbot.repositories;

import capstone.chatbot.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepo extends JpaRepository<Message, Long> {
    List<Message> findAllByConversation(Long id);
}
