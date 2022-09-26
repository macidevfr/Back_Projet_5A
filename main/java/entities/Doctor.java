package entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;

@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long    id;
    String  name;
    String  firstName;
    @Column(name = "Age", nullable = false)
    int     age;
    @ManyToOne
    Center centerDoc;

    public Doctor(String name, String firstName, int age, Center centerDoc) {
        this.name = name;
        this.firstName = firstName;
        this.age = age;
        this.centerDoc = centerDoc;
    }
}
