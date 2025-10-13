package com.puzzlix.solid_task.domain.user.login;

import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
GOOGLE 타입으로 로그인 전략 구현
 */

@RequiredArgsConstructor
@Component
public class GoogleLoginStrategy implements LoginStrategy{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(UserRequest.Login request) {
        //TODO 도전과제
        //1.클라이언트에게 받은 구글토큰
        //2.구글서버로부터 사용자 정보(이메일, 이름 등)
        //3.해당이메일을 우리 DB 조회 후 없으면 회원가입, 있으면 로그인 처리
        return null;
    }

    @Override
    public boolean supports(String type) {
        return "GOOGLE".equalsIgnoreCase(type);
    }
}