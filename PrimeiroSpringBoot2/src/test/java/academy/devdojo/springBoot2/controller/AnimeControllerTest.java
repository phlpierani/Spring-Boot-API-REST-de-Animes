package academy.devdojo.springBoot2.controller;

import academy.devdojo.springBoot2.domain.Anime;
import academy.devdojo.springBoot2.requests.AnimePostRequestBody;
import academy.devdojo.springBoot2.requests.AnimePutRequestBody;
import academy.devdojo.springBoot2.service.AnimeService;
import academy.devdojo.springBoot2.util.AnimeCreator;
import academy.devdojo.springBoot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springBoot2.util.AnimePutRequestBodyCreator;
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

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks // Cria uma instância do AnimeController e injeta os mocks
    private AnimeController animeController;

    @Mock // Cria um mock do AnimeService
    private AnimeService animeService;

    @BeforeEach
        // Executa antes de cada teste
    void setUp() {
        // Configurações iniciais antes de cada teste, se necessário
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidUpdatedAnime()));
        BDDMockito.when(animeService.listALL(ArgumentMatchers.any()))
                .thenReturn(animePage); // Configura o comportamento do mock para o método listALL
        // Retorna uma página de animes com um anime válido

        // Configura o comportamento do mock para o método listALLNonPageable
        BDDMockito.when(animeService.listALLNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidName()));

        BDDMockito.when(animeService.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                // Retorna um anime válido quando o ID é passado
                .thenReturn(AnimeCreator.createValidName());

        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidName()));
        // Retorna uma lista de animes com um anime válido quando o nome é passado

        BDDMockito.when(animeService.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidName());
        // Retorna um anime válido quando um AnimePostRequestBody é passado

        BDDMockito.doNothing().when(animeService).replace(ArgumentMatchers.any(AnimePutRequestBody.class));
        // Configura o comportamento do mock para o método replace

        BDDMockito.doNothing().when(animeService).delete(ArgumentMatchers.anyLong());
        // Configura o comportamento do mock para o método delete
    }

    @Test
    @DisplayName("list returns list of animes inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidName().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull(); // Verifica se a página de animes não é nula

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1); // Verifica se a lista de animes não está vazia e tem tamanho 1

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
        // Verifica se o nome do anime na lista é igual ao nome esperado
    }

    // Teste para o método listAll
    @Test
    @DisplayName("listALL returns list of animes  when successful")
    void listALL_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidName().getName();
        List<Anime> animes = animeController.listAll().getBody();


        Assertions.assertThat(animes)
                .isNotNull() // Verifica se a lista de animes não é nula
                .isNotEmpty() // Verifica se a lista de animes não está vazia
                .hasSize(1); // Verifica se a lista de animes não está vazia e tem tamanho 1

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
        // Verifica se o nome do anime na lista é igual ao nome esperado
    }

    // Teste para o método findByID
    @Test
    @DisplayName("findByID returns list of animes  when successful")
    void findByID_ReturnAnime_WhenSuccessful() {
        Long exptectedID = AnimeCreator.createValidName().getId();
        Anime anime = animeController.findById(1).getBody();

        Assertions.assertThat(anime)
                .isNotNull(); // Verifica se a lista de animes não é nula

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(exptectedID);
        // Verifica se o nome do anime na lista é igual ao nome esperado
    }

    // Teste para o método findbyName
    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidName().getName();
        List<Anime> animes = animeController.findByname("anime").getBody();


        Assertions.assertThat(animes)
                .isNotNull() // Verifica se a lista de animes não é nula
                .isNotEmpty() // Verifica se a lista de animes não está vazia
                .hasSize(1); // Verifica se a lista de animes não está vazia e tem tamanho 1

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    // Teste para o método save
    @Test
    @DisplayName("save returns list of animes  when successful")
    void save_ReturnAnime_WhenSuccessful() {

        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
        // Chama o método save do AnimeController e passa um AnimePostRequestBody
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidName());
        // Verifica se a lista de animes não é nula

    }

    // Teste para o método update
    @Test
    @DisplayName("replace updates animes  when successful")
    void replace_UpdateAnime_WhenSuccessful() {

        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException(); // Verifica se não lança exceção
        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull(); // Verifica se a lista de animes não é nula
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        // Verifica se o código de status da resposta é NO_CONTENT (204)

    }

    // Teste para o método delete
    @Test
    @DisplayName("delete remove animes  when successful")
    void delete_UpdateAnime_WhenSuccessful() {

        Assertions.assertThatCode(() -> animeController.delete(20))
                .doesNotThrowAnyException(); // Verifica se não lança exceção

        ResponseEntity<Void> entity = animeController.delete(20);

        Assertions.assertThat(entity).isNotNull(); // Verifica se a lista de animes não é nula
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        // Verifica se o código de status da resposta é NO_CONTENT (204)
    }
}