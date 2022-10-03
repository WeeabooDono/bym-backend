package com.wd.bym.batch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MalMangaResponse {

    private List<MangaNode> data = new ArrayList<>();

    private MalPaging paging;
}
