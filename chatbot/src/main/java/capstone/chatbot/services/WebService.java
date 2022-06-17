package capstone.chatbot.services;

import capstone.chatbot.entities.Conversation;
import capstone.chatbot.entities.Message;
import capstone.chatbot.entities.QnA;
import capstone.chatbot.entities.User;
import capstone.chatbot.repositories.ChatRepo;
import capstone.chatbot.repositories.ConversationRepo;
import capstone.chatbot.repositories.QnARepo;
import capstone.chatbot.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WebService {

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    ConversationRepo convRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    QnARepo qRepo;


    public Conversation createConversation(String anonymous) {
        Conversation conversation = new Conversation();
        conversation.setUser("Anonymous");
        conversation.setStartTime( new Date());
        convRepo.save( conversation );

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

        List<QnA> qnAList =  qRepo.findAllByQuestion(message);

        if(qnAList.isEmpty()){
            return "Sorry, i can't answer this question! Try one of the keywords below: " +
                    "Campus Size <br>" +
                    "Campus Color <br>" +
                    "Number of Departments <br>" +
                    "Hi <br>" +
                    "Benefits <br>" +
                    "University Clubs <br>" +
                    "Campus Buildings <br>" +
                    "Annoucements <br>" +
                    "Enrollment Process <br>";
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

    public List<Conversation> getConversations() {

        return convRepo.findAll();
    }

    public List<Message> getMessages(Long id) {

        return chatRepo.findAllByConversation(id);
    }
}