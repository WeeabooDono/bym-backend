package com.wd.bym.batch.job.MmlMangaRetriever.reader;

import com.wd.bym.batch.domain.MalMangaResponse;
import com.wd.bym.batch.service.MalService;
import com.wd.bym.batch.transform.MalRequest;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class MalMangaPageItemReader implements ItemReader<MalMangaResponse> {

    public static MalMangaResponse response = null;
    private final MalService malService;
    private final MalRequest initialRequest;

    @Override
    public MalMangaResponse read() throws Exception {
        if (response != null) {
            var nextUrl = response.getPaging().getNext();
            if (nextUrl == null) {
                // TODO: find a better way, but it needs to be reset for any potential batch running in the same context
                response = null;
                return null;
            }
            var splitQuery = splitQuery(new URL(nextUrl));
            this.initialRequest.setOffset(Integer.parseInt(splitQuery.get("offset")));
            this.initialRequest.setLimit(Integer.parseInt(splitQuery.get("limit")));
        }

        response = malService.mangaPageRequest(this.initialRequest);

        return response;
    }

    public static Map<String, String> splitQuery(URL url) {
        var queryPairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(
                    URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }

}
