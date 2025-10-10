package com.puzzlix.solid_task.domain.issue.dto;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository //이 클래스가 데이터 저장소 역할을 하는 스프링빈이다.
public class MemoryIssueRepository implements IssueRepository {

    //멀티쓰레드에서..
    //동시성 문제를 방지하기 위해 ConcurrentHashMap 사용
    private static Map<Long, Issue> store = new ConcurrentHashMap<>();

    private static AtomicLong sequence = new AtomicLong(0);

    @Override
    public Issue save(Issue issue) {

        //save요청시 Issue에 상태값 id가 없는 상태
        if (issue.getId() == null) {
            issue.setId(sequence.incrementAndGet());
        }

        store.put(issue.getId(), issue);

        return issue;
    }

    @Override
    public Optional<Issue> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Issue> findAll() {
        return new ArrayList<>(store.values());
    }
}
