package com.wd.bym.batch.service;

import com.wd.bym.batch.transform.MalRequest;
import com.wd.bym.core.domain.Manga;

public interface MalService {

    Manga mangaRequest(MalRequest malRequest, int id);
}
