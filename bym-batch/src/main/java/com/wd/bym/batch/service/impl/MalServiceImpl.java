package com.wd.bym.batch.service.impl;

import com.wd.bym.batch.configuration.MalProperties;
import com.wd.bym.batch.domain.MalMangaResponse;
import com.wd.bym.batch.service.MalService;
import com.wd.bym.batch.transform.MalRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

import static com.wd.bym.batch.transform.MalRankingParameter.*;

@Service
@Slf4j
@AllArgsConstructor
public class MalServiceImpl implements MalService {

    private final MalProperties malProperties;

    @Override
    public MalMangaResponse mangaPageRequest(MalRequest malRequest) {
        var baseUrl = malProperties.getUrl() + "/" + malProperties.getApiVersion() + "/manga/ranking";
        baseUrl += "?" + RANKING_TYPE.getValue() + "=" + malRequest.getRankingType().getValue();
        baseUrl += "&" + OFFSET.getValue() + "=" + malRequest.getOffset();
        baseUrl += "&" + LIMIT.getValue() + "=" + malRequest.getLimit();
        baseUrl += "&" + FIELDS.getValue() + "=" + malRequest.getComaSeparatedFields();

        log.info("[GET] fetching at {}", baseUrl);
        final WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(malProperties.getClientHeader(), malProperties.getClientId());
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();

        return getResponse(webClient);
    }

    private MalMangaResponse getResponse(WebClient webClient) {
        return Objects.requireNonNull(webClient.get()
                        .retrieve()
                        .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), clientResponse -> {
                            // TODO: manage errors
                            try {
                                throw new Exception();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toEntity(MalMangaResponse.class)
                        .block())
                .getBody();
    }
}
