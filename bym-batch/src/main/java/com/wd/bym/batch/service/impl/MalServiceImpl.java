package com.wd.bym.batch.service.impl;

import com.wd.bym.batch.configuration.MalProperties;
import com.wd.bym.batch.service.MalService;
import com.wd.bym.batch.transform.MalRequest;
import com.wd.bym.core.domain.Manga;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MalServiceImpl implements MalService {

    private final MalProperties malProperties;
    private final Environment environment;

    @Override
    public Manga mangaRequest(MalRequest malRequest, int id) {
        final WebClient webClient = WebClient.builder()
                .baseUrl(malProperties.getUrl() + "/" + malProperties.getApiVersion() + "/manga/" + id)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(malProperties.getClientHeader(), malProperties.getClientId());
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();

        return Objects.requireNonNull(webClient.get()
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), clientResponse -> {
//                    // TODO: manage errors
//                    try {
//                        throw new Exception();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
                    return null;
                })
                .toEntity(Manga.class)
                .block()).getBody();
    }
}
