package com.puzzlix.solid_task.domain.issue.dto;

import lombok.Getter;
import lombok.Setter;

public class IssueRequest {

    //모바일에서, 클라이언트서, 이슈 생성요청

    @Getter
    @Setter
    //static inner class
    public static class Create {

        //클라이언트가 직접 입력해야 하는 정보, 또는 세팅돼야할 정보

        private String title;
        private String description;
        private Long projectId;
        private Long reporterId;
    }

    //IssueRequest.Create dto = new IssueRequest.Create(...);

    @Getter
    @Setter
    public static class Update {
        private String title;
        private String description;
        private Long assigneeId;
    }
}
