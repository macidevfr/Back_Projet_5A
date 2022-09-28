package JPA;

import Entities.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ICenter extends JpaRepository<Center,Long> {

}
