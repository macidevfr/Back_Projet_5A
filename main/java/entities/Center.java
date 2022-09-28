package Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Center {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long    id;
    String  name;
    @ManyToOne
    City    city;
    @OneToMany
    List<Doctor> centres;

    public Center(String name, City city) {
        this.name = name;
        this.city = city;
    }

}
