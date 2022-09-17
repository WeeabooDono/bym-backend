package com.wd.bym.core.transform.mapper;

import com.wd.bym.core.domain.Manga;
import com.wd.bym.core.transform.dto.MangaDto;
import com.wd.bym.core.transform.dto.MangaSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MangaMapper {

    public abstract List<MangaDto> toDTOs(List<Manga> mangas);

    public abstract MangaDto toDTO(Manga manga);

    @Mapping(target = "entityVersion", ignore = true)
    public abstract Manga toEntity(MangaDto manga);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entityVersion", ignore = true)
    public abstract Manga toEntity(MangaSaveDto manga);
}
