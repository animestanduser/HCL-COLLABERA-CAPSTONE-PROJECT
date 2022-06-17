package capstone.api.services;

import capstone.api.entities.Conversation;
import capstone.api.entities.Message;
import capstone.api.entities.QnA;
import capstone.api.entities.User;
import capstone.api.repositories.ChatRepo;
import capstone.api.repositories.ConversationRepo;
import capstone.api.repositories.QnARepo;
import capstone.api.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    ConversationRepo conversationRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    QnARepo qnaRepo;


    public Conversation createConversation(String anonymous) {
        Conversation  conversation = new Conversation();
        conversation.setUser("Anonymous");
        conversation.setStarttime( new Date());
        conversationRepo.save( conversation );

        return  conversation;
    }

    public void saveChat(Long index, String user, String msg , String owner) {
        Message message = new Message();
        message.setMessage(msg);
        message.setUser(user);
        message.setOwner(owner);
        message.setConversation(index);
        message.setTime( new Date());
        chatRepo.save(message);
    }

    public String isInQnA(String message) {

        List<QnA> qnAList =  qnaRepo.findAllByQuestion(message);

        if(qnAList.isEmpty()){
            return "Sorry! Couldnt help this moment. Try something else....";
        } else {
            return qnAList.get(0).getAnswer();
        }

    }

    public void saveUser(User user) {
        userRepo.save(user);

    }

    public User getUser(String username) {

        return userRepo.findAllByEmail(username);

    }

    public List<Conversation> getConversations(String username) {
        return conversationRepo.findAllByUser(username);
    }

    public List<Message> getMessages(Long id , String user) {
        return chatRepo.findAllByConversationAndUser(id , user);
    }

    public List<Conversation> getAdminConversations() {
        return conversationRepo.findAll();
    }

    public List<Message> getAdminMessages(Long id ) {
        return chatRepo.findAllByConversation(id );
    }
}