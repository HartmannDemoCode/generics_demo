package dk.cphbusiness.dat.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "person") // Avoid infinite recursion
public class Phone {

    @Id
    @Column(name = "phonenumber", nullable = false)
    private String number;

    private String description;

    @ManyToOne
    private Person person;

    @Builder
    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }
}