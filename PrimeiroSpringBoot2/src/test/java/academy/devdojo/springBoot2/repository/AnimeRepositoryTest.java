package academy.devdojo.springBoot2.repository;

import academy.devdojo.springBoot2.domain.Anime;
import academy.devdojo.springBoot2.util.AnimeCreator;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hibernate.query.sqm.tree.SqmNode.log;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Test for AnimeRepository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    //teste para salvar
    @Test
    @DisplayName("save persists Anime when successful")
    void save_PersistAnime_WhenSucessful(){
        Anime createAnimeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(createAnimeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull(); // Verifica se o anime salvo não é nulo
        Assertions.assertThat(animeSaved.getId()).isNotNull(); // Verifica se o ID do anime salvo não é nulo
        Assertions.assertThat(animeSaved.getName()).isEqualTo(createAnimeToBeSaved.getName());
        // Verifica se o nome do anime salvo é igual ao nome do anime que foi salvo
    }

    // teste para o update
    @Test
    @DisplayName("save updateAnime when successful")
    void save_UpdateAnime_WhenSucessful(){
        Anime createAnimeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(createAnimeToBeSaved);

        animeSaved.setName("overload");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        log.info(animeUpdated.getName()); // Loga o nome do anime atualizado
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("delete remove when successful")
    void save_DeleteAnime_WhenSucessful() {
        Anime createAnimeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(createAnimeToBeSaved);

        this.animeRepository.delete(animeSaved); // Exclui o anime salvo

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId()); // Verifica se o anime existe no banco de dados

        Assertions.assertThat(animeOptional).isEmpty(); // Verifica se o anime não existe mais no banco de dados
    }

    // teste para exceções
    @Test
    @DisplayName("save throws ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        Anime anime = new Anime(); // id e name null
         //Tenta salvar e espera exceção
        Assertions.assertThatThrownBy(() -> animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);
        // Verifica se a exceção é do tipo ConstraintViolationException

//            Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
//                    .isThrownBy(() -> animeRepository.save(anime)) // Verifica se a exceção é lançada
//                    .withMessageContaining("The anime name cannot be empty");
//            // Verifica se a mensagem da exceção contém a mensagem esperada
    }

}