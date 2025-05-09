package academy.devdojo.springBoot2.util;

import academy.devdojo.springBoot2.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Boku no Hero")
                .build();
    }
    public static Anime createValidName() {
        return Anime.builder()
                .name("Boku no Hero")
                .id(1L)
                .build();
    }
    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("Boku no Hero")
                .id(1L)
                .build();
    }
}
