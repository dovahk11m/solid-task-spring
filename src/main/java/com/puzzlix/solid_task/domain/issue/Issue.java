package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.comment.Comment;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id  ")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id  ")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id  ")
    private User assignee;

    //댓글
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
