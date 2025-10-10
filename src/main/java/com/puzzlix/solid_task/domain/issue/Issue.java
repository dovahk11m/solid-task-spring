package com.puzzlix.solid_task.domain.issue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Issue {

    //pk
    //타이틀
    //내용
    //진행상태

    //프로젝트pk
    //보고자
    //담당자

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING) //DB서는 String으로
    private IssueStatus issueStatus;

    //TODO 추후 연결
    private Long projectId;
    private Long reporterId;
    private Long assigneeId;
}
