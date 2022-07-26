package com.keplux.todo_app.subtask;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {

  List<Subtask> findByTaskId(Long taskId);
}
