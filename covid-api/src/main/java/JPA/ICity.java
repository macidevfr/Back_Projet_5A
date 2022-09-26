package JPA;

import entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICity extends JpaRepository<City,Long> {

}
