package academy.devdojo.sprinboot2essentialsexercicio.util;

import academy.devdojo.sprinboot2essentialsexercicio.domain.Anime;
import academy.devdojo.sprinboot2essentialsexercicio.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequestBody createAnimePostRequestBody() {
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
