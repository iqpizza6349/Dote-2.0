package org.iqpizza6349.dote.global.util.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iqpizza6349.dote.global.configuration.WebClientConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientUtils {

    private final WebClientConfiguration webClientConfiguration;

    public <T> Mono<T> requestGet(String url, Class<T> responseType, String header) {
        log.info("url: {}", url);
        log.info("header: {}", header);
        return webClientConfiguration.webClient()
                .get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, header)
                .header(HttpHeaders.ACCEPT, "*/*")
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T, V extends BodyInserter<?, ? super ClientHttpRequest>> Mono<T> requestPost(String url, V value, Class<T> responseType) {
        return webClientConfiguration.webClient()
                .post()
                .uri(url)
                .body(value)
                .retrieve()
                .bodyToMono(responseType);
    }


}
