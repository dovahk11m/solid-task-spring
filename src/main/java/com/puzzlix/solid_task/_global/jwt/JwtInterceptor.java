package com.puzzlix.solid_task._global.jwt;

import com.puzzlix.solid_task.domain.user.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        //1.요청메시지 헤더에서 키값 Authorization 헤더를 찾아 JWT 토큰을 추출
        //2.순수 토큰 추출
        String token = resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            //true && true

            //request 이메일, UserRole 넘겨주기
            String userEmail = jwtTokenProvider.getSubject(token);
            UserRole userRole = jwtTokenProvider.getRole(token);
            request.setAttribute("userEmail", userEmail);
            request.setAttribute("userRole", userRole);

            return true;
        }
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "유효하지 않은 토큰"
        );
        return false;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        //토큰검증, Bearer 제거
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
