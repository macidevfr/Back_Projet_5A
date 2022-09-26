package JPA;

import entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctor extends JpaRepository<Doctor,Long> {

}
