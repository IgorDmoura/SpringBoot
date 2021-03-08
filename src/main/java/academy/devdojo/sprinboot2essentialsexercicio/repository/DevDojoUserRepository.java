package academy.devdojo.sprinboot2essentialsexercicio.repository;

import academy.devdojo.sprinboot2essentialsexercicio.domain.Anime;
import academy.devdojo.sprinboot2essentialsexercicio.domain.DevDojoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long> {

    DevDojoUser findByUsername(String username);

}
