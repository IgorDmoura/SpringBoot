package academy.devdojo.sprinboot2essentialsexercicio.repository;

import academy.devdojo.sprinboot2essentialsexercicio.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
