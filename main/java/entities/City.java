package entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class City {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long         id;
    String       name;
    Long         zipCode;
    @OneToMany
    List<Center> centres;

    public City(String name, Long zipCode, List<Center> centres) {
        this.name = name;
        this.zipCode = zipCode;
        this.centres = centres;
    }
    public City(String name, Long zipCode) {
        this.name = name;
        this.zipCode = zipCode;
    }
}
