package org.iqpizza6349.dote.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iqpizza6349.dote.domain.member.dto.LoginRequest;
import org.iqpizza6349.dote.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public Mono<?> login(@RequestBody LoginRequest loginRequest) {
        return memberService.login(loginRequest);
    }

    @GetMapping("/test")
    public Mono<?> test(@RequestHeader("Authorization") String token) {
        return memberService.test(token);
    }

    @GetMapping
    public Mono<String> a() {
        log.info("Hello, Webflux!");
        return Mono.just("Hello, Spring!");
    }

}
