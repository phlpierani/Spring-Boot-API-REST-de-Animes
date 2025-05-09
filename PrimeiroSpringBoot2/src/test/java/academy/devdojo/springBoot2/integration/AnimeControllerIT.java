package academy.devdojo.springBoot2.integration;

import academy.devdojo.springBoot2.domain.Anime;
import academy.devdojo.springBoot2.repository.AnimeRepository;
import academy.devdojo.springBoot2.requests.AnimePostRequestBody;
import academy.devdojo.springBoot2.util.AnimeCreator;
import academy.devdojo.springBoot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springBoot2.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// Inicia o contexto do Spring Boot com uma porta aleatória
@AutoConfigureTestDatabase // Configura o banco de dados para usar um banco de dados em memória (H2) para testes
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Limpa o contexto após cada teste
class AnimeControllerIT {
        @Autowired
        @Qualifier(value = "testRestTemplateRoleUser") // Injeta o TestRestTemplate com autenticação de usuário

        private TestRestTemplate testRestTemplate;

//        @LocalServerPort // Inicia o servidor na porta aleatória
//        private int port;

        @Autowired
        private AnimeRepository animeRepository;


        @Test
        @DisplayName("List returns list of anime inside page object when successful")
        void list_ReturnsListOfAnime_WhenSuccessful() {
               Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
                String expectedName = AnimeCreator.createValidName().getName();

                PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                        new ParameterizedTypeReference<PageableResponse<Anime >> () {
                }).getBody();

                Assertions.assertThat(animePage).isNotNull(); // Verifica se a página de animes não é nula
                Assertions.assertThat(animePage.toList()).isNotEmpty() // Verifica se a lista de animes não está vazia
                        .hasSize(1); // Verifica se a lista de animes tem tamanho 1

                Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
                // Verifica se o nome do anime é igual ao esperado
        }

    @Test
    @DisplayName("listALL returns list of animes  when successful")
    void listALL_ReturnsListOfAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = AnimeCreator.createValidName().getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime >> () {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull() // Verifica se a página de animes não é nula
                .isNotEmpty() // Verifica se a lista de animes não está vazia
                .hasSize(1); // Verifica se a lista de animes tem tamanho 1

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    // Teste para o método findByID
    @Test
    @DisplayName("findByID returns list of animes  when successful")
    void findByID_ReturnAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        Long exptectedID = savedAnime.getId();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, exptectedID);

        Assertions.assertThat(anime)
                .isNotNull(); // Verifica se a lista de animes não é nula

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(exptectedID);
        // Verifica se o nome do anime na lista é igual ao nome esperado
    }

    // Teste para o método findbyName
    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnListOfAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = AnimeCreator.createValidName().getName();

        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime >> () {
                }).getBody();

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
            AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

            ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull(); // Verifica se a lista de animes não é nula
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull(); // Verifica se o código de status da resposta não é nulo
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull(); // Verifica se o ID do anime não é nulo
    }

    // Teste para o método update
    @Test
    @DisplayName("replace updates animes  when successful")
    void replace_UpdateAnime_WhenSuccessful() {

           Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

           savedAnime.setName("new name");

        ResponseEntity<Void>animeResponseEntity = testRestTemplate.exchange(
                "/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull(); // Verifica se a lista de animes não é nula
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    // Teste para o método delete
    @Test
    @DisplayName("delete remove animes  when successful")
    void delete_UpdateAnime_WhenSuccessful() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void>animeResponseEntity = testRestTemplate.exchange(
                "/animes/{id}",HttpMethod.DELETE,null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull(); // Verifica se a lista de animes não é nula
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
