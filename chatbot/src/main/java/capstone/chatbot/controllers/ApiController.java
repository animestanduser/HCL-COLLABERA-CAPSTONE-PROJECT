package capstone.chatbot.controllers;

import capstone.chatbot.entities.Conversation;
import capstone.chatbot.entities.Message;
import capstone.chatbot.entities.User;
import capstone.chatbot.utilities.ChatInput;
import capstone.chatbot.services.WebService;
import capstone.chatbot.utilities.ChatBody;
import capstone.chatbot.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
public class ApiController {

    @Autowired
    WebService webService;

    @ResponseBody
    @RequestMapping(value = "/web/api/hello" , method = RequestMethod.POST)
    public ResponseEntity<Response> hello(@RequestBody ChatBody messageBody){
        String user =  "anonymous";

        Authentication authetication = SecurityContextHolder.getContext().getAuthentication();

        if( authetication != null && authetication.getPrincipal() instanceof UserDetails ){
            UserDetails userDetails = (UserDetails) authetication.getPrincipal() ;
            user = userDetails.getUsername();
        }

        if( messageBody.getIndex() == 0){
            Conversation con = webService.createConversation( user );
            messageBody.setIndex( con.getId() );
        }

        webService.saveChat( messageBody.getIndex(), user , messageBody.getMessage() , "user" );


        String botResponse = satbot(messageBody.getMessage() );

        Response response = new Response();
        response.setStatus("success");
        response.setData(botResponse);
        response.setConvId(messageBody.getIndex());

        webService.saveChat( messageBody.getIndex(), user , botResponse , "bot" );

        return new ResponseEntity<>( response , HttpStatus.OK);
    }


    public String satbot(String message ){

        Authentication authetication = SecurityContextHolder.getContext().getAuthentication();

        if( authetication != null && authetication.getPrincipal() instanceof UserDetails ){
            UserDetails userDetails = (UserDetails) authetication.getPrincipal() ;
            String[] userpropeties = { "name" , "email" , "department" , "faculty" , "mobile"};
            if(Arrays.stream(userpropeties).toList().contains( message.toLowerCase())){
                User user = webService.getUser(userDetails.getUsername());
                switch ( message.toLowerCase() ){
                    case "name" : return user.getName();
                    case "email" : return user.getEmail();
                    case "department" : return user.getDepartment();
                    case "faculty" : return user.getFaculty();
                    case "mobile" : return user.getMobile();
                }
            }
        }

        String qna = webService.isInQnA( message );
        return qna;
    }

    @ResponseBody
    @RequestMapping(value = "/web/admin/conversations" , method = RequestMethod.POST)
    public ResponseEntity<List<Conversation>> conversations(){
        List<Conversation> response = webService.getConversations();
        return new ResponseEntity<>( response , HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/web/admin/messages" , method = RequestMethod.POST)
    public ResponseEntity<List<Message>> messages(@RequestBody ChatInput chatInput){
        List<Message> response = webService.getMessages( chatInput.getId() );
        return new ResponseEntity<>( response , HttpStatus.OK);
    }
}