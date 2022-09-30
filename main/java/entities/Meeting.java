package Entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long         id;
    @ManyToOne
    Center       center;
    Date         debuted;
    Boolean      reserved;

    public Meeting(Center center, Date debuted, Boolean reserved) {
        this.center = center;
        this.debuted = debuted;
        this.reserved = reserved;
    }
}
