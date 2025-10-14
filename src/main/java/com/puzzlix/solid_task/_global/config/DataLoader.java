package com.puzzlix.solid_task._global.config;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueRepository;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import com.puzzlix.solid_task.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //샘플데이터 추가하기

        //User
        User testUser1 = userRepository.save(new User(
                null,
                "홍길동",
                "test1@naver.com",
                passwordEncoder.encode("1234"),
                UserRole.USER,
                new ArrayList<>()
        ));
        User testUser2 = userRepository.save(new User(
                null,
                "장길산",
                "test2@naver.com",
                passwordEncoder.encode("1234"),
                UserRole.USER,
                new ArrayList<>()
        ));
        User admin1 = userRepository.save(new User(
                null,
                "관리자1",
                "admin1@naver.com",
                passwordEncoder.encode("1234"),
                UserRole.ADMIN,
                new ArrayList<>()
        ));


        //Project
        Project testProject = projectRepository.save(new Project(
                null,
                "SOLID TASK 프로젝트",
                "설명",
                new ArrayList<>()
        ));

        //Issue
        Issue issue1 = issueRepository.save(new Issue(
                null,
                "로그인 기능 구현",
                "JWT 필요",
                IssueStatus.TODO,
                testProject,
                testUser1,
                null,
                new ArrayList<>()
        ));
        Issue issue2 = issueRepository.save(new Issue(
                null,
                "검색 기능 구현 요청",
                "이슈 전체 목록에 검색 기능 필요",
                IssueStatus.TODO,
                testProject,
                testUser1,
                null,
                new ArrayList<>()
        ));

    }
}
