package org.iqpizza6349.dote.global.util.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.iqpizza6349.dote.domain.member.dao.MemberRepository;
import org.iqpizza6349.dote.domain.member.entity.Member;
import org.iqpizza6349.dote.global.constants.JsonWebTokenType;
import org.iqpizza6349.dote.global.exception.BusinessException;
import org.iqpizza6349.dote.global.property.JwtProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonWebTokenProvider {

    private static final long JWT_ACCESS_EXPIRE = 1000 * 60 * 60 * 24;
    private static final long JWT_REFRESH_EXPIRE = 1000 * 60 * 60 * 24 * 7;

    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

    private Key getTokenSecretKey(String secret) {
        byte[] keyBytes = Base64.decodeBase64(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String userId, JsonWebTokenType jwtAuth) {
        Date expiredAt = new Date();
        expiredAt = (jwtAuth == JsonWebTokenType.ACCESS_TOKEN)
                ? new Date(expiredAt.getTime() + JWT_ACCESS_EXPIRE)
                : new Date(expiredAt.getTime() + JWT_REFRESH_EXPIRE);
        Key secretKey = getTokenSecretKey((jwtAuth == JsonWebTokenType.ACCESS_TOKEN)
                ? jwtProperties.getAccessSecret()
                : jwtProperties.getRefreshSecret());

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(jwtAuth.toString())
                .setIssuedAt(new Date())
                .setExpiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }

    private Claims parseToken(String token, JsonWebTokenType jwtAuth) {
        try {
            Key secretKey = getTokenSecretKey((jwtAuth == JsonWebTokenType.ACCESS_TOKEN)
                    ? jwtProperties.getAccessSecret()
                    : jwtProperties.getRefreshSecret());
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "위조된 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다.");
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "토큰 서비스와의 오류가 발생하였습니다.");
        }
    }

    public Member validateToken(String token) {
        return memberRepository.findById(parseToken(token, JsonWebTokenType.ACCESS_TOKEN)
                        .get("userId").toString())
                .blockOptional()
                .orElseThrow(Member.NotFoundException::new);
    }

    public String refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다.");
        }

        Claims claims = parseToken(refreshToken, JsonWebTokenType.REFRESH_TOKEN);
        Member member = memberRepository.findById(claims.get("userId").toString())
                .blockOptional(Duration.ofSeconds(1))
                .orElseThrow(Member.NotFoundException::new);

        return generateToken(member.getId(), JsonWebTokenType.ACCESS_TOKEN);
    }

}
