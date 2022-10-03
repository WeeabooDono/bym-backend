package com.wd.bym.batch.service;

import com.wd.bym.batch.domain.MalMangaResponse;
import com.wd.bym.batch.transform.MalRequest;

public interface MalService {

    MalMangaResponse mangaPageRequest(MalRequest malRequest);
}
