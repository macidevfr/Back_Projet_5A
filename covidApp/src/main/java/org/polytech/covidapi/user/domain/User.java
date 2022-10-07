package org.polytech.covidapi.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.polytech.covidapi.entities.Center;
import org.polytech.covidapi.user.enumeration.Role;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String firstName;
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String phone;
    private Role role;
    private String[] authorities;
    private boolean active = true;
    private boolean notLocked = true;
    @ManyToOne()
    @JoinColumn(name = "center_id")
    @JsonIgnoreProperties("usersList")
    Center center;




}
