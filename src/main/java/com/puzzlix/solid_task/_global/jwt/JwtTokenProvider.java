package com.puzzlix.solid_task._global.jwt;

// JJWT 0.12.x API 사용

import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component //IoC 대상
public class JwtTokenProvider {

    private final SecretKey key;
    private final Long validityInMilliseconds;

    //생성자 주입 설계 (주입시 연산을 해야할 경우)
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-in-ms}") Long validityInMilliseconds
    ) {
        //1.주입받은 비밀키 문자열을 Base64값으로 디코딩
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        //2.알고리즘을 사용할 secretKey 객체를 생성
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.validityInMilliseconds = validityInMilliseconds;
    }

    //로그인시 새 토큰 생성
    public String createToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        //TODO 속성 공부
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getUserRole().name())
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * 토큰의 전체 유효성 검증
     *
     * @param token 검증할 JWT
     * @return boolean 유효성 여부
     */

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error(
                    "잘못된 JWT 서명입니다",
                    e
            );
        } catch (ExpiredJwtException e) {
            log.error(
                    "잘못된 JWT 토큰입니다",
                    e
            );
        } catch (UnsupportedJwtException e) {
            log.error(
                    "지원되지 않는 JWT 토큰입니다",
                    e
            );
        } catch (Exception e) {
            log.error(
                    "JWT 토큰이 잘못됐습니다",
                    e
            );
        }
        return false;
    }

    /**
     * 토큰에서 사용자 이메일 추출
     *
     * @param token
     * @return String 추출된 사용자 이메일
     */
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 토큰에서 사용자 Role 추출
     * @param token
     * @return
     */
    public UserRole getRole(String token){
        String role = parseClaims(token).get("role", String.class);
        return UserRole.valueOf(role); //스트링값에 맞는 이넘 반환
    }

    //클레임 정보를 추출하는 기능
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            //토큰이 만료되면 클레임 자체를 반환해 만료정보 확인
            return e.getClaims();
        }
    }
}
