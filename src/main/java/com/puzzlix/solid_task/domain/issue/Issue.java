package com.puzzlix.solid_task.domain.issue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Issue {

    //pk
    //타이틀
    //내용
    //진행상태

    //프로젝트pk
    //보고자
    //담당자

    private Long id;
    private String title;
    private String description;
    private IssueStatus issueStatus;

    //TODO 추후 연결
    private Long projectId;
    private Long reporterId;
    private Long assigneeId;
}
