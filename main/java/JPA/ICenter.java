package JPA;

import Entities.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICenter extends JpaRepository<Center,Long> {

}
