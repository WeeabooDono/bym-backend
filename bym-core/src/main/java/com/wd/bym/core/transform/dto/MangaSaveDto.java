package com.wd.bym.core.transform.dto;

import com.wd.bym.core.constraint.UniqueMangaTitle;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MangaSaveDto {

    @UniqueMangaTitle
    @NotNull
    private String title;
}
