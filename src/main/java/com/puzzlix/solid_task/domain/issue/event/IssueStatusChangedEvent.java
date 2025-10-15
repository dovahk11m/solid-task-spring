package com.puzzlix.solid_task.domain.issue.event;

import com.puzzlix.solid_task.domain.issue.Issue;
import lombok.Getter;

/*
스프링 이벤트를 사용하여
이벤트 클래스로 설계한다
느슨한 커플링을 활용한다
 */
@Getter
public class IssueStatusChangedEvent {

    private final Issue issue;

    public IssueStatusChangedEvent(Issue issue) {
        this.issue = issue;
    }
}
