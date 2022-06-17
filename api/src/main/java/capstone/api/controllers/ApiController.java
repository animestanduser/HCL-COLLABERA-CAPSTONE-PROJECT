package capstone.api.controllers;

import capstone.api.entities.Conversation;
import capstone.api.entities.Message;
import capstone.api.entities.User;
import capstone.api.utilities.ChatInput;
import capstone.api.services.ApiService;
import capstone.api.utilities.ChatBody;
import capstone.api.utilities.Response;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ApiController {

    @Autowired
    ApiService apiService;


    @ResponseBody
    @RequestMapping(value = "/api/admin/conversations" , method = RequestMethod.POST)
    public ResponseEntity<List<Conversation>> conversations(){
        List<Conversation> response = apiService.getAdminConversations();
        return new ResponseEntity<>( response , HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/messages" , method = RequestMethod.POST)
    public ResponseEntity<List<Message>> messages(@RequestBody ChatInput chatInput){
        List<Message> response = apiService.getAdminMessages(  chatInput.getId() );
        return new ResponseEntity<>( response , HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/conversations" , method = RequestMethod.POST)
    public ResponseEntity<List<Conversation>> conversationsuser(HttpServletRequest request){

        String authHeader = request.getHeader(AUTHORIZATION);

        if( authHeader != null && authHeader.substring(0 ,7 ).equals("Bearer ")){
            try {
                String token = authHeader.substring( "Bearer ".length() );
                Algorithm algorithm = Algorithm.HMAC512( "sattyda".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();

                List<Conversation> response = apiService.getConversations(username);
                return new ResponseEntity<>( response , HttpStatus.OK);


            } catch (Exception e){
                return new ResponseEntity<>(  HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<>(  HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/messages" , method = RequestMethod.POST)
    public ResponseEntity<List<Message>> messagesuser(@RequestBody ChatInput chatInput , HttpServletRequest request){

        String authHeader = request.getHeader(AUTHORIZATION);

        if( authHeader != null && authHeader.substring(0 ,7 ).equals("Bearer ")){
            try {
                String token = authHeader.substring( "Bearer ".length() );
                Algorithm algorithm = Algorithm.HMAC512( "sattyda".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();

                List<Message> response = apiService.getMessages( chatInput.getId() , username );
                return new ResponseEntity<>( response , HttpStatus.OK);

            } catch (Exception e){
                return new ResponseEntity<>(  HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<>(  HttpStatus.FORBIDDEN);
    }

}