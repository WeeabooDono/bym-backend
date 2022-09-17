package com.wd.bym.core.transform.dto.pagination;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PagerDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5028568162225889804L;

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int pageSize = 0;

    @Builder.Default
    private String sortField = "id";

    @Builder.Default
    private Sort.Direction sortOrder = Sort.DEFAULT_DIRECTION;

    public PageRequest toPageRequest() {
        Sort sort;
        PageRequest pageRequest;
        if (StringUtils.isNotEmpty(sortField)) {
            sort = Sort.by(sortOrder, sortField);
            pageRequest = PageRequest.of(page, pageSize, sort);
        } else {
            pageRequest = PageRequest.of(page, pageSize);
        }
        return pageRequest;
    }
}
