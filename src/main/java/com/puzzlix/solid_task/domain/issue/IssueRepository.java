package com.puzzlix.solid_task.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

/*
저장소 역할(규칙)을 정의하는 인터페이스
 */

public interface IssueRepository extends JpaRepository<Issue, Long> {

//    Issue save(Issue issue);
//
//    Optional<Issue> findById(Long id);
//
//    List<Issue> findAll();
}
