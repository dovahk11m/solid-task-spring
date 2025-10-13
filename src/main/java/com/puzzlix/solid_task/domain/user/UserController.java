package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task._global.dto.CommonResponseDTO;
import com.puzzlix.solid_task._global.jwt.JwtTokenProvider;
import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO<?>> signUp(
            @Valid @RequestBody UserRequest.SignUp request
    ) {
        userService.signUp(request);
        return ResponseEntity.ok(CommonResponseDTO.success(
                null,
                "회원가입 완료"
        ));
    }

    @PostMapping("/login/{type}")
    public ResponseEntity<CommonResponseDTO<?>> login(
            @PathVariable String type,
            @Valid @RequestBody UserRequest.Login request
    ) {
        User user = userService.login(type, request);
        //이메일을 기반으로 토큰 생성
        String token = jwtTokenProvider.createToken((user.getEmail()));
        return ResponseEntity.ok(CommonResponseDTO.success(
                token,
                "로그인 완료"
        ));
    }
}
