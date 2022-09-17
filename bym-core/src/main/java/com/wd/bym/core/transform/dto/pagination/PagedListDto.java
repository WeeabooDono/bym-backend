package com.wd.bym.core.transform.dto.pagination;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedListDto<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7298375620832559291L;

    @Builder.Default
    private transient List<T> items = new ArrayList<>();

    @Builder.Default
    private long total = 0;

    @Builder.Default
    private PagerDto pager = PagerDto.builder().build();
}
