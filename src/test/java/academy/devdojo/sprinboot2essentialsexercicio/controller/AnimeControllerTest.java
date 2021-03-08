package academy.devdojo.sprinboot2essentialsexercicio.controller;

import academy.devdojo.sprinboot2essentialsexercicio.domain.Anime;
import academy.devdojo.sprinboot2essentialsexercicio.requests.AnimePostRequestBody;
import academy.devdojo.sprinboot2essentialsexercicio.requests.AnimePutRequestBody;
import academy.devdojo.sprinboot2essentialsexercicio.service.AnimeService;
import academy.devdojo.sprinboot2essentialsexercicio.util.AnimeCreator;
import academy.devdojo.sprinboot2essentialsexercicio.util.AnimePostRequestBodyCreator;
import academy.devdojo.sprinboot2essentialsexercicio.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class) //Usa o JUnit como spring
class AnimeControllerTest {
    @InjectMocks //Quando quer testar a classe em si
    private AnimeController animeController;

    @Mock //Utiliza para todas as classes que estao dentro do AnimeController - Ex:DateUtil, animerservice etc...
    private AnimeService animeServiceMock;

    @BeforeEach //Antes de cada teste executa isso
    void SetUp(){
        //Quando alguem executa o listall returna o anime page
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidName()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidName()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidName());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidName()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidName());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());


    }

    @Test
    @DisplayName("ListAll returns list of anime when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidName().getName();

        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList()).isNotEmpty();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("List returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidName().getName();

        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }


    @Test
    @DisplayName("FindById returns anime when successful")
    void FindById_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidName().getId();

        Anime anime = animeController.findByID(1).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("FindByName returns a list of anime when successful")
    void FindByName_ReturnsListOfAnime_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidName().getName();

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindByName returns an empty list of anime when anime is not found")
    void FindByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime).isEqualTo(AnimeCreator.createValidName());

    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){
        Assertions.assertThatCode(()->animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Assertions.assertThatCode(()->animeController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}