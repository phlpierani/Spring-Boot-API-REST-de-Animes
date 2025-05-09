package academy.devdojo.springBoot2.client;

import academy.devdojo.springBoot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {

//        // RestTemplate = GetforEntity
//        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/20", Anime.class);
//        log.info(entity);
//
//        // RestTemplate = GetforObject
//        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/20", Anime.class);
//        log.info(object);

//        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
//        log.info(Arrays.toString(animes));

//        // RestTemplate = exchange
//        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
//                HttpMethod.GET,null,
//                new ParameterizedTypeReference<List<Anime>>(){});
//        log.info(exchange.getBody());
//
//        // RestTemplate = postForObject
//        Anime kingdom = Anime.builder().name("Kingdom").build();
//        Anime kingdomSaver = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
//        log.info("saved anime{}", kingdomSaver);
//
//        //RestTemplate = putForEntity
//        Anime animeToUpdate = Anime.builder()
//                .id(30L) // ID do anime que você quer atualizar
//                .name("Kingdom 2")
//                .build();
//
//        new RestTemplate().put("http://localhost:8080/animes", animeToUpdate);
//        log.info("Anime atualizado: {}", animeToUpdate);

//        // RestTemplate = Delete
//        String url = "http://localhost:8080/animes/30";
//        new RestTemplate().delete(url);
//        log.info("Anime com ID 30 deletado com sucesso");

    }
}
