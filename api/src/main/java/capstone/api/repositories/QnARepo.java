package capstone.api.repositories;

import capstone.api.entities.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepo extends JpaRepository<QnA, Long> {
    List<QnA> findAllByQuestion(String message);
}