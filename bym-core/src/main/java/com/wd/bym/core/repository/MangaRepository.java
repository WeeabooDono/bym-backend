package com.wd.bym.core.repository;

import com.wd.bym.core.domain.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {
    Optional<Manga> findMangaByTitle(String title);
}
