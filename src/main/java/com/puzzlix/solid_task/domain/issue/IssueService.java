package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import com.puzzlix.solid_task.domain.user.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Issue updateIssueStatus(
            Long issueid,
            IssueStatus issueStatus,
            String userEmail
    ) {
        User requestUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("해당 유저 없음"));
        Issue issue = issueRepository.findById(issueid).orElseThrow(() -> new NoSuchElementException("해당 이슈 없음"));
        boolean isAdmin = requestUser.getUserRole().equals(UserRole.ADMIN);
        boolean isAssignee = requestUser.getId().equals(issue.getAssignee().getId());
        if (!isAdmin && !isAssignee) {
            throw new SecurityException("수정 권한 없음");
        }
        issue.setIssueStatus(issueStatus);
        return issue;
    }

    /*
    이슈 수정 로직
    1.이슈 조회
    2.
    3.
    4.JPA사용 (수정전략) -> dirty checking
     */
    @Transactional
    public Issue updateIssue(
            Long issueId,
            IssueRequest.Update request,
            String userEmail
    ) {
        User requestUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("해당 유저 없음"));
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new NoSuchElementException("해당 이슈 없음"));
        boolean isAdmin = requestUser.getUserRole().equals(UserRole.ADMIN);
        boolean isReporter = requestUser.getId().equals(issue.getReporter().getId());
        if (!isAdmin && !isReporter) {
            throw new SecurityException("수정 권한 없음");
        }
        //담당자 할당 여부에 따른 분기처리
        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(() -> new NoSuchElementException("해당 담당자 없음"));
            //실제 담당자 할당
            issue.setAssignee(assignee);
        } else {
            //담당자 해제 처리
            issue.setAssignee(null);
        }
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        //JPA 변경 감지(Dirty Checking) 덕분에
        //save()를 명시적으로 호출하지 않아도
        //Transaction이 끝날때 변경된 내용을 감지해서 DB에 반영
        return issue;
    }

    //이슈 삭제 로직
    @Transactional
    public void deleteIssue(
            Long issueId,
            String userEmail
    ) {
        User requestUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("해당 유저 없음"));
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new NoSuchElementException("해당 이슈 없음"));
        boolean isAdmin = requestUser.getUserRole().equals(UserRole.ADMIN);
        boolean isReporter = requestUser.getId().equals(issue.getReporter().getId());

        if (!isAdmin && !isReporter) {
            throw new SecurityException("삭제 권한 없음");
        }
        issueRepository.delete(issue);
        //TODO 삭제 이력 관리 feat.userEmail
    }

    //이슈 생성 로직
    @Transactional
    public Issue createIssue(IssueRequest.Create request) {
        //보고자 ID 조회
        User reporter = userRepository.findById(request.getReporterId()).orElseThrow(() -> new NoSuchElementException("해당 ID의 사용자를 찾을 수 없습니다"));
        //프로젝트 ID 검증
        Project project = projectRepository.findById(request.getProjectId()).orElseThrow(() -> new NoSuchElementException("해당 ID의 프로젝트를 찾을 수 없습니다"));
        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());
        newIssue.setReporter(reporter);
        newIssue.setProject(project);
        newIssue.setIssueStatus(IssueStatus.TODO);
        return issueRepository.save(newIssue);
    }

    //모든 이슈 조회
    public List<Issue> findIssue() {
        return issueRepository.findAll();
    }
}
