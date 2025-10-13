package com.puzzlix.solid_task.domain.issue;

/*
단위테스트 vs 통합테스트

IssueServiceTest = 단위테스트

Mockito 프레임워크를 Junit5와 연동

 */

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //가짜객체만들기
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private IssueService issueService;

    //이슈생성, 이슈조회 테스트

    /**
     * 테스트 메서드
     * 새로운 이슈 생성시 기능 테스트
     * 1.클라이언트가 이슈 생성 요청
     * 2.서비스는 필요한 연관 엔티티 조회
     * 3.새로운 이슈 객체 생성, 연관관계 설정
     * 4.이슈 저장
     */
    @Test
    @DisplayName("새로운 이슈를 성공적으로 생성")
    void createIssue_withMapping() {
        //given
        //1.클라이언트에 요청을 DTO로준비
        IssueRequest.Create request = new IssueRequest.Create();
        request.setTitle("이슈 생성 테스트 확인");
        request.setDescription("단위 테스트 통과 여부 확인");
        request.setProjectId(1L);
        request.setProjectId(1L);
        //2.레파지토리 조회시 반환될 가짜 엔티티 준비
        User mockReporter = new User(
                1L,
                "홍길동",
                "test@test.com",
                "1234",
                null
        );
        Project mockProject = new Project(
                1L,
                "솔리드테스트",
                "설명",
                null
        );

        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockReporter));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        when(issueRepository.save(any(Issue.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Issue createIssue = issueService.createIssue(request);

        //then
        Assertions.assertThat(createIssue.getTitle()).isEqualTo("이슈 생성 테스트 확인");
        Assertions.assertThat(createIssue.getDescription()).isEqualTo(IssueStatus.TODO);
        // 연관관계가 올바르게 설정 되었는지 객체 자체를 비교해서 검증
        Assertions.assertThat(createIssue.getReporter()).isEqualTo(mockReporter);
        Assertions.assertThat(createIssue.getProject()).isEqualTo(mockProject);
        // 추가 검증 - 서비스 로직이 실제로 findById 를 호출 했는지 확인
        verify(userRepository).findById(1L);
        verify(projectRepository).findById(1L);
    }

    /*
    목 객체는 기본적으로 빈 껍데기다.
    실제 동작처럼 시나리오를 미리 작성해줘야 한다.
     */

}
