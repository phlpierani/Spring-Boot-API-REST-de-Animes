package academy.devdojo.springBoot2.util;

import academy.devdojo.springBoot2.domain.Anime;
import academy.devdojo.springBoot2.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequestBody createAnimePostRequestBody() {
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
