package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
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

//    DI처리
//    public IssueService(IssueRepository issueRepository){
//        this.issueRepository = issueRepository
//    }

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
