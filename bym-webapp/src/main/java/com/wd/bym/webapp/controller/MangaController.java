package com.wd.bym.webapp.controller;

import com.wd.bym.core.service.MangaService;
import com.wd.bym.core.transform.dto.MangaDto;
import com.wd.bym.core.transform.dto.MangaSaveDto;
import com.wd.bym.core.transform.dto.pagination.PagedListDto;
import com.wd.bym.core.transform.dto.pagination.PagerDto;
import com.wd.bym.webapp.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mangas")
public class MangaController {

    private final MangaService mangaService;

    @GetMapping
    public ResponseEntity<PagedListDto<MangaDto>> getAll(PagerDto pager) {
        return ResponseEntity.ok(this.mangaService.getAll(pager));
    }

    @GetMapping("{id}")
    public ResponseEntity<MangaDto> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.mangaService.getOne(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(NotFoundException.NotFoundExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ResponseEntity.BodyBuilder> create(@RequestBody MangaSaveDto manga) {
        var savedManga = this.mangaService.create(manga);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + savedManga.getId().toString()).build().toUri()).build();
    }
}
