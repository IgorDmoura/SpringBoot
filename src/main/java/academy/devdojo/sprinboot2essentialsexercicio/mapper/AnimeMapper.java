package academy.devdojo.sprinboot2essentialsexercicio.mapper;

import academy.devdojo.sprinboot2essentialsexercicio.domain.Anime;
import academy.devdojo.sprinboot2essentialsexercicio.requests.AnimePostRequestBody;
import academy.devdojo.sprinboot2essentialsexercicio.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
