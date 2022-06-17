package capstone.chatbot.repositories;

import capstone.chatbot.entities.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepo extends JpaRepository<QnA , Long> {
    List<QnA> findAllByQuestion(String message);
}
