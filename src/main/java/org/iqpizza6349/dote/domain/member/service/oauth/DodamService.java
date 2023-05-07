package org.iqpizza6349.dote.domain.member.service.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iqpizza6349.dote.domain.member.dto.oauth.DAuthRequestDto;
import org.iqpizza6349.dote.domain.member.dto.oauth.DAuthServerDto;
import org.iqpizza6349.dote.domain.member.dto.oauth.OpenApiDto;
import org.iqpizza6349.dote.global.constants.BaseUrl;
import org.iqpizza6349.dote.global.property.DodamProperties;
import org.iqpizza6349.dote.global.util.webclient.WebClientUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DodamService {

    private final DodamProperties properties;
    private final WebClientUtils webClientUtils;

    // remember, do not use `block()`

    public Mono<?> test(final String token) {
        log.info("send request to dodam user server");
        return webClientUtils.requestGet(
                BaseUrl.OPEN_API.getEndPoint() + "/user",
                OpenApiDto.class,
                token
        );
    }

    public Mono<OpenApiDto> getCodeToDodamInfo(final String code) {
        log.info("----- dodam server request -----");
        Mono<DAuthServerDto> serverMono = getDAuthToken(code);
        return serverMono.log().flatMap(serverDto -> {
            log.info(formatToken(serverDto.getAccessToken()));
            return webClientUtils.requestGet(
                    BaseUrl.OPEN_API.getEndPoint() + "/user",
                    OpenApiDto.class,
                    formatToken(serverDto.getAccessToken())
            );
        }).log();
    }

    private Mono<DAuthServerDto> getDAuthToken(String code) {
        log.info("----- dauth server request -----");
        return webClientUtils.requestPost(
                BaseUrl.AUTH.getEndPoint() + "/token",
                BodyInserters.fromValue(new DAuthRequestDto(code, properties.getClientId(), properties.getClientSecret())),
                DAuthServerDto.class
        );
    }

    private String formatToken(final String accessToken) {
        //noinspection StringBufferReplaceableByString
        return new StringBuffer("Bearer ").append(accessToken).toString();
    }
}
