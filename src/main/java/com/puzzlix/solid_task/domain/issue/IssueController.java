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
    특정 이슈 수정 by (담당자, 관리자)
    PATCH
    http://localhost:8080/api/issues/{id}/status?=DONE
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<CommonResponseDTO<?>> updateIssueStatus(
            @PathVariable(name = "id") Long issueId,
            @RequestParam("status") IssueStatus newStatus,
            @RequestAttribute("userEmail") String userEmail
    ) {
        Issue issue = issueService.updateIssueStatus(
                issueId,
                newStatus,
                userEmail
        );
        //DTO에 담기
        IssueResponse.FindById response = new IssueResponse.FindById(issue);

        return ResponseEntity.ok(CommonResponseDTO.success(response, "이슈 상태 변경 성공"));
    }

    /*
    이슈 수정 API
    PUT
    /api/issues/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponseDTO<IssueResponse.FindById>> updateIssue(
            @PathVariable(name = "id") Long id,
            @RequestBody IssueRequest.Update request,
            @RequestAttribute("userEmail") String userEmail
    ) {
        Issue issue = issueService.updateIssue(
                id,
                request,
                userEmail
        );
        IssueResponse.FindById findByIdDto = new IssueResponse.FindById(issue);
        return ResponseEntity.ok(CommonResponseDTO.success(
                findByIdDto,
                "이슈 변경 성공"
        ));
    }

    /*
    이슈 삭제 API
    DELETE/issues/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(
            @PathVariable(name = "id") Long id,
            @RequestAttribute("userEmail") String userEmail
    ) {
        issueService.deleteIssue(
                id,
                userEmail
        );
        return ResponseEntity.ok(CommonResponseDTO.success(
                null,
                "이슈 삭제 성공"
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
