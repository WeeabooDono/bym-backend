package com.wd.bym.core.service.impl;

import com.wd.bym.core.annotation.ReadTx;
import com.wd.bym.core.annotation.Tx;
import com.wd.bym.core.domain.Manga;
import com.wd.bym.core.repository.MangaRepository;
import com.wd.bym.core.service.MangaService;
import com.wd.bym.core.transform.dto.MangaDto;
import com.wd.bym.core.transform.dto.MangaSaveDto;
import com.wd.bym.core.transform.dto.pagination.PagedListDto;
import com.wd.bym.core.transform.dto.pagination.PagerDto;
import com.wd.bym.core.transform.mapper.MangaMapper;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MangaServiceImpl implements MangaService {

    private final MangaRepository repository;
    private final MangaMapper mapper;

    public MangaServiceImpl(MangaRepository mangaRepository, MangaMapper mangaMapper) {
        this.repository = mangaRepository;
        this.mapper = mangaMapper;
    }

    @Override
    @ReadTx
    public PagedListDto<MangaDto> getAll(PagerDto pager) {
        var pagedMangas = this.repository.findAll(pager.toPageRequest());
        return new PagedListDto<>(this.mapper.toDTOs(pagedMangas.getContent()), pagedMangas.getTotalElements(), pager);
    }

    @Override
    @ReadTx
    public MangaDto getOne(Long id) {
        var manga = this.findById(id);
        return this.mapper.toDTO(manga);
    }

    @Override
    @Tx
    public MangaDto create(MangaSaveDto dto) {
        var manga = this.repository.save(this.mapper.toEntity(dto));
        return this.mapper.toDTO(manga);
    }

    private Manga findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Manga with id " + id + " was not found !"));
    }
}
