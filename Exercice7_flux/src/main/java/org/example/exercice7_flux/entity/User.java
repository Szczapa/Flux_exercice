package org.example.exercice7_flux.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collation = "fr")
public class User {
    @Id
    private int id;
    private String username;
    private String email;
    private boolean active;

    public User(String username, String email, boolean active) {
        this.username = username;
        this.email = email;
        this.active = active;
    }
}
