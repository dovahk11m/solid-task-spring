package com.puzzlix.solid_task.domain.issue.dto;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class IssueResponse {

    @Getter
    //모든 이슈 보여주기, 단일 이슈 보여주기
    public static class FindAll {
        private final Long id;
        private final String title;
        private final IssueStatus issueStatus;

        //생성자를 private 선언
        private FindAll(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.issueStatus = issue.getIssueStatus();
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
}
