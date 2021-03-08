package academy.devdojo.sprinboot2essentialsexercicio.util;

import academy.devdojo.sprinboot2essentialsexercicio.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }

    public static Anime createValidName(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .id(1l)
                .build();
    }

    public static Anime createValidUpdatedAnime(){
        return Anime.builder()
                .name("Hajime no Ippo 2")
                .id(1L)
                .build();
    }
}
