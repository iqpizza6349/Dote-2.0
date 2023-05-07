package org.iqpizza6349.dote.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.iqpizza6349.dote.domain.member.dao.MemberRepository;
import org.iqpizza6349.dote.domain.member.dto.LoginRequest;
import org.iqpizza6349.dote.domain.member.dto.LoginResponse;
import org.iqpizza6349.dote.domain.member.dto.oauth.OpenApiDto;
import org.iqpizza6349.dote.domain.member.entity.Member;
import org.iqpizza6349.dote.domain.member.service.oauth.DodamService;
import org.iqpizza6349.dote.global.constants.JsonWebTokenType;
import org.iqpizza6349.dote.global.util.jwt.JsonWebTokenProvider;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final DodamService dodamService;
    private final MemberRepository memberRepository;
    private final ReactiveMongoTemplate mongoTemplate;
    private final JsonWebTokenProvider tokenProvider;

    public Mono<?> test(String token) {
        return dodamService.test(token);
    }

    public Mono<?> login(LoginRequest loginRequest) {
        Mono<OpenApiDto> apiDtoMono = dodamService.getCodeToDodamInfo(loginRequest.getCode());

        return mongoTemplate.save(
                apiDtoMono.flatMap(openApiDto -> Mono.just(Member.builder()
                        .grade(openApiDto.getInfo().getGrade())
                        .room(openApiDto.getInfo().getRoom())
                        .number(openApiDto.getInfo().getNumber())
                        .name(openApiDto.getInfo().getName())
                        .build()
        ))).flatMap(m -> Mono.just(new LoginResponse(
                        tokenProvider.generateToken(m.getId(), JsonWebTokenType.ACCESS_TOKEN),
                        tokenProvider.generateToken(m.getId(), JsonWebTokenType.REFRESH_TOKEN)
        )));
    }


}
