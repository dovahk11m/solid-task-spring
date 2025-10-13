package com.puzzlix.solid_task.domain.comment;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"issue", "writer"}) // 순환참조 방지
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    //연관관계 (작성자, 게시물)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

}
