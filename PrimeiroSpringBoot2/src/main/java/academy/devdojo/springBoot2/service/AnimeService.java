package academy.devdojo.springBoot2.service;
import academy.devdojo.springBoot2.Exceptions.BadRequestException;
import academy.devdojo.springBoot2.domain.Anime;
import academy.devdojo.springBoot2.repository.AnimeRepository;
import academy.devdojo.springBoot2.requests.AnimePostRequestBody;
import academy.devdojo.springBoot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import academy.devdojo.springBoot2.mapper.AnimeMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.data.domain.Page;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;

    public Page<Anime> listALL(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> listALLNonPageable() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    public Anime save(AnimePostRequestBody body) {
        // Verifique o corpo da requisição para garantir que o nome não seja null
        if (body.getName() == null || body.getName().isEmpty()) {
            throw new BadRequestException("Nome do anime não pode ser vazio");
        }

        Anime anime = animeMapper.toAnime(body);  // Mapeia o DTO para a entidade
        return animeRepository.save(anime);  // Salva a entidade no banco de dados
    }

    public void delete(long id) {
        Anime animeToDelete = findByIdOrThrowBadRequestException(id);
        animeRepository.delete(animeToDelete);
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = animeMapper.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }
}
