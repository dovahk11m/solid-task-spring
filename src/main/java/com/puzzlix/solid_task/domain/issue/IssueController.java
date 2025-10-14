package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task._global.dto.CommonResponseDTO;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.dto.IssueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/issues")
@RestController
public class IssueController {

    private final IssueService issueService;

    /*
    이슈 수정 API
    PUT
    /api/issues/{issueId}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponseDTO<IssueResponse.FindById>> updateIssue(
            @PathVariable(name = "id") Long id,
            @RequestBody IssueRequest.Update request
    ) { //인증검사, 유효성검사
        Issue issue = issueService.updateIssue(
                id,
                request
        );
        IssueResponse.FindById findByIdDto = new IssueResponse.FindById(issue);
        return ResponseEntity.ok(CommonResponseDTO.success(
                findByIdDto,
                "이슈 변경 성공"
        ));
    }


    /*
    이슈 생성 API
    POST
    /api/issues
     */
    @PostMapping
    public ResponseEntity<CommonResponseDTO<Issue>> createIssue(@RequestBody IssueRequest.Create request) {
        Issue createdIssue = issueService.createIssue(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDTO.success(createdIssue));
    }

    /*
    이슈 목록조회 API
    GET
    /api/issues
     */
    @GetMapping
    public ResponseEntity<CommonResponseDTO<List<IssueResponse.FindAll>>> getIssues() {
        //서비스 조회 요청
        List<Issue> issues = issueService.findIssue();

        //조회된 도메인 이슈 리스트를 DTO로 변환
        List<IssueResponse.FindAll> responseDtos = IssueResponse.FindAll.from(issues);
        return ResponseEntity.ok(CommonResponseDTO.success(responseDtos));
    }

}
