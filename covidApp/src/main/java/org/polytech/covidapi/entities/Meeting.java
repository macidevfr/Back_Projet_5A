package org.polytech.covidapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Meeting {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long         id;
    @ManyToOne
    Center       center;
    @JsonFormat(pattern="dd/MM/yyyy")
    Date         appointment;
    String       nom,prenom,mail;
    Boolean      vaccinated = false;

    public Meeting(Center center, Date appointment, String nom, String prenom, String mail, Boolean vaccinated) {
        this.center         = center;
        this.appointment    = appointment;
        this.nom            = nom;
        this.prenom         = prenom;
        this.mail           = mail;
        this.vaccinated     = vaccinated;
    }
}
