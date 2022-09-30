package Entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Vaccination {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long         id;
    Long         nbDoses=0L;
    Boolean      isVaccinated;

    public Vaccination(Long nbDoses, Boolean isVaccinated) {
        this.nbDoses = nbDoses;
        this.isVaccinated = isVaccinated;
    }

    public void beVaccinated(){
        this.nbDoses+=1;
    }

}
