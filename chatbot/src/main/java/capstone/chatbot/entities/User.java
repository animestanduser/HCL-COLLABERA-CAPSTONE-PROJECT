package capstone.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String email;

    String name;

    String password;

    String role;

    Boolean enabled = true;

    String department;

    String mobile;

    String faculty;

    String type;
}
