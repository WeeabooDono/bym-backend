package com.wd.bym.batch.job.MmlMangaRetriever.reader;

import com.wd.bym.batch.service.MalService;
import com.wd.bym.batch.transform.MalRequest;
import com.wd.bym.core.domain.Manga;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

@AllArgsConstructor
public class MalMangaItemReader implements ItemReader<Manga> {

    public static int id = 1;
    private static final List<Integer> unexistingIds = List.of(5, 6, 82);

    //    private final MalProperties malProperties;
    private final MalService malService;

    // unexisting ids : 5, 82
    @Override
    public Manga read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (id > 2) {
            return null;
        }
        id++;
//        while (unexistingIds.contains(id)) {
//            id++;
//        }

        Manga manga = null;
        while (manga == null) {
            manga = malService.mangaRequest(new MalRequest(List.of("title")), id);
        }
        return manga;
    }

}
