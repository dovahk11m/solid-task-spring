package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //앱컨피그 소속

    //회원가입, 로그인

    /*
    회원가입 절차
    1.이메일중복확인
    2.비밀번호암호화
    3.유저 저장
     */
    public User signUp(UserRequest.SignUp request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 이메일");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        return userRepository.save(newUser);
    }

    /*
    로그인 절차
    1.사용자조회
    2.입력된 비번과 암호화된 비번 비교
     */
    public User login(UserRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일"));
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new IllegalArgumentException("잘못된 비밀번호");
        }
        return user;
    }


}
