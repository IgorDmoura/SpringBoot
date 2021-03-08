package academy.devdojo.sprinboot2essentialsexercicio.response;

import academy.devdojo.sprinboot2essentialsexercicio.domain.Anime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnimePostResponseBody {
    private String name;
    private Long id;

    public AnimePostResponseBody toAnimePostResponseBody(Anime anime) {
        this.name = anime.getName();
        this.id = anime.getId();
        return this;
    }
}


