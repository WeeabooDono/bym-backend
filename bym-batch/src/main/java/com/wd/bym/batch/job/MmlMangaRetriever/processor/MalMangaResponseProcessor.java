package com.wd.bym.batch.job.MmlMangaRetriever.processor;

import com.wd.bym.batch.domain.MalMangaResponse;
import com.wd.bym.batch.domain.MangaNode;
import com.wd.bym.core.domain.Manga;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class MalMangaResponseProcessor implements ItemProcessor<MalMangaResponse, List<Manga>> {

    @Override
    public List<Manga> process(MalMangaResponse item) throws Exception {
        return item.getData().stream().map(MangaNode::getNode).toList();
    }
}
