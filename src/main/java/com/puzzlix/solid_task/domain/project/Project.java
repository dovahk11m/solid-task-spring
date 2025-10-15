package com.puzzlix.solid_task.domain.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = "issues")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    //무조건 양방향 설정을 할 필요는 없다.
    //ProjectRepository 에 직접 쿼리를 만들자

//    //
//    @OneToMany(mappedBy = "project")
//    private List<Issue> issues = new ArrayList<>();
}
