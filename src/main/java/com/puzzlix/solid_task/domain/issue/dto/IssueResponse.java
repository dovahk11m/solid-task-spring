package com.puzzlix.solid_task.domain.issue.dto;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class IssueResponse {

    @Getter
    //모든 이슈 보여주기, 단일 이슈 보여주기
    public static class FindAll {
        private final Long id;
        private final String title;
        private final IssueStatus issueStatus;
        private final String reporterName;

        //생성자를 private 선언
        private FindAll(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.issueStatus = issue.getIssueStatus();
            this.reporterName = issue.getReporter().getName();
            //Lazy 로딩 전략을 사용할깨
            //issue 안에 포함된 user를 탐색하면
            //추가 쿼리가 발생한다.
            //이것이 N+1 문제다.
        }

        //정적 팩토리 메서드 선언 (제네릭이 아님)
        //엔티티를 DTO로 변환해준다.
        //IssueResponse.from([issue, issue, issue])
        public static List<FindAll> from(List<Issue> issues) {
            List<FindAll> dtoList = new ArrayList<>();
            for (Issue issue : issues) {
                dtoList.add(new FindAll(issue));
            }
            return dtoList;
        }
    }

    @Getter
    @Setter
    public static class FindById {
        private final Long id;
        private final String title;
        private final String description;
        private final IssueStatus status;
        //연관관계
        private final String projectName;
        private final String reporterName;
        private final String assigneeName;

        //엔티티를 DTO로 변환하는 생성자
        public FindById(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.description = issue.getDescription();
            this.status = issue.getIssueStatus();
            this.projectName = issue.getProject().getName();
            this.reporterName = issue.getReporter().getName();
            //담당자 Nullable
            this.assigneeName = issue.getAssignee() != null ? issue.getAssignee().getName() : null;
        }
    }
}
