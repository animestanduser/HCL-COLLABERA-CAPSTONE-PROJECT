package capstone.chatbot;

import capstone.chatbot.entities.QnA;
import capstone.chatbot.repositories.QnARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatbotApplication {

	@Autowired
	QnARepo qnaRepo;

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

	@Bean
	public void addData(){
		qnaRepo.save(new QnA( null, "Campus Size" , "1200acres" , "Campus" , "CAMPUS"));
		qnaRepo.save(new QnA( null, "Campus Color" , "Green" , "Campus" , "CAMPUS" ));
		qnaRepo.save(new QnA( null, "Number of Departments" , "10" , "Department" ,
				"DEPARTMENTS" ));
		qnaRepo.save(new QnA( null, "Hi" , "Hello, how can i help you?" ,
				"Greeting" ,"GREETING" ));
		qnaRepo.save(new QnA( null, "Benefits" , "" , "Benefits" ,"BENEFITS" ));
		qnaRepo.save(new QnA( null, "University Clubs" ,
				"Our university provides a large number of extracurricular activities, a development path for " +
						"athletes, artists, scientists and much more" , "Benefits" ,"BENEFITS" ));
		qnaRepo.save(new QnA( null, "Campus Buildings" , "Our campus provides a swimming pool, " +
				"library, gardens, sports fields, science rooms" , "Benefits" ,"BENEFITS" ));
		qnaRepo.save(new QnA( null, "Annoucements" , "Currently no annoucments" , "News" ,
				"NEWS" ));
		qnaRepo.save(new QnA( null, "Enrollment Process" , "We are not currently recruiting" ,
				"News" ,"NEWS" ));
	}
}
