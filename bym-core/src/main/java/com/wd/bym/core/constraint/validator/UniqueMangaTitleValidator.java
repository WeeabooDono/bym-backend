package com.wd.bym.core.constraint.validator;

import com.wd.bym.core.constraint.UniqueMangaTitle;
import com.wd.bym.core.repository.MangaRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueMangaTitleValidator implements ConstraintValidator<UniqueMangaTitle, String> {

    private final MangaRepository mangaRepository;

    public UniqueMangaTitleValidator(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        return title == null || mangaRepository.findMangaByTitle(title).isEmpty();
    }
}
