package academy.devdojo.springBoot2.mapper;

import academy.devdojo.springBoot2.domain.Anime;
import academy.devdojo.springBoot2.requests.AnimePostRequestBody;
import academy.devdojo.springBoot2.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
