package com.puzzlix.solid_task.domain.user.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

//로그인 전략을 찾아주는 공장
@RequiredArgsConstructor
@Component
public class LoginStrategyFactory {

    //로컬전략, 구글전략.. 다 가지고 있다.
    //스프링이 @Component 등록된 모든 LoginStrategy 를 DI해줌
    private final List<LoginStrategy> strategies;

    //들어온 type에 맞는 전략을 찾아서 반환
    public LoginStrategy findStrategy(String type) {
        //주입받은 모든 전략을 순회
        for (LoginStrategy strategy : strategies) {
            if (strategy.supports(type)) {
                return strategy;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 로그인 타입");
    }
}
