package dk.cphbusiness.dat.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "person") // Avoid infinite recursion
public class Phone implements IJPAEntity<String> {

    @Id
    @Column(name = "phonenumber", nullable = false)
    private String number;

    private String description;

    @ManyToOne
    private Person person;

    public String getId() {
        return number;
    }

    @Builder
    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }
}