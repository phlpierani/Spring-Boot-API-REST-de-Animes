package academy.devdojo.springBoot2.service;

import academy.devdojo.springBoot2.domain.Anime;
import academy.devdojo.springBoot2.repository.AnimeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
        @InjectMocks // Cria uma instância do AnimeController e injeta os mocks
        private AnimeService animeService;

        @Mock // Cria um mock do AnimeService
        private AnimeRepository animeRepositoryMock;

        @BeforeEach // Executa antes de cada teste
        void setUp() {
            // Configurações iniciais antes de cada teste, se necessário
            PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidUpdatedAnime()));
                when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                    .thenReturn(animePage); // Configura o comportamento do mock para o método listALL
                // Retorna uma página de animes com um anime válido

            // Configura o comportamento do mock para o método listALLNonPageable
            BDDMockito.when(animeRepositoryMock.findAll())
                    .thenReturn(List.of(AnimeCreator.createValidName()));

            BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                    // Retorna um anime válido quando o ID é passado
                    .thenReturn(Optional.of(AnimeCreator.createValidName()));

            BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                    .thenReturn(List.of(AnimeCreator.createValidName()));
            // Retorna uma lista de animes com um anime válido quando o nome é passado

            BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                    .thenReturn(AnimeCreator.createValidName());
            // Retorna um anime válido quando um AnimePostRequestBody é passado

            BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
            // Configura o comportamento do mock para o método delete
        }

        @Test
        @DisplayName("list returns list of animes inside page object when successful")
        void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
            String expectedName = AnimeCreator.createValidName().getName();
            Page<Anime> animePage = animeService.listALL(PageRequest.of(1,1));

            Assertions.assertThat(animePage).isNotNull(); // Verifica se a página de animes não é nula

            Assertions.assertThat(animePage.toList())
                    .isNotEmpty()
                    .hasSize(1); // Verifica se a lista de animes não está vazia e tem tamanho 1

            Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
            // Verifica se o nome do anime na lista é igual ao nome esperado
        }

        // Teste para o método listAll
        @Test
        @DisplayName("listALLNonPageable returns list of animes  when successful")
        void listALL_ReturnsListOfAnimes_WhenSuccessful() {
            String expectedName = AnimeCreator.createValidName().getName();
            List<Anime> animes = animeService.listALLNonPageable();


            Assertions.assertThat(animes)
                    .isNotNull() // Verifica se a lista de animes não é nula
                    .isNotEmpty() // Verifica se a lista de animes não está vazia
                    .hasSize(1); // Verifica se a lista de animes não está vazia e tem tamanho 1

            Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
            // Verifica se o nome do anime na lista é igual ao nome esperado
        }

        // Teste para o método findByID
        @Test
        @DisplayName("findByIdOrThrowBadRequestException returns list of animes  when successful")
        void findByID_ReturnAnime_WhenSuccessful() {
            Long exptectedID = AnimeCreator.createValidName().getId();
            Anime anime = animeService.findByIdOrThrowBadRequestException(1);

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
            List<Anime> animes = animeService.findByName("anime");

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

            Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());
            // Chama o método save do AnimeController e passa um AnimePostRequestBody
            Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidName());
            // Verifica se a lista de animes não é nula

        }

        // Teste para o método update
        @Test
        @DisplayName("replace updates animes  when successful")
        void replace_UpdateAnime_WhenSuccessful() {

            Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                    .doesNotThrowAnyException(); // Verifica se não lança exceção

        }

        // Teste para o método delete
        @Test
        @DisplayName("delete remove animes  when successful")
        void delete_RemoveAnime_WhenSuccessful() {

            Assertions.assertThatCode(() -> animeService.delete(20))
                    .doesNotThrowAnyException(); // Verifica se não lança exceção

        }
    }
