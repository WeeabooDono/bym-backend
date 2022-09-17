package com.wd.bym.core.service;

import com.wd.bym.core.transform.dto.MangaDto;
import com.wd.bym.core.transform.dto.MangaSaveDto;
import com.wd.bym.core.transform.dto.pagination.PagedListDto;
import com.wd.bym.core.transform.dto.pagination.PagerDto;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface MangaService {

    PagedListDto<MangaDto> getAll(PagerDto pager);

    MangaDto getOne(Long id);

    MangaDto create(@Valid MangaSaveDto manga);
}
