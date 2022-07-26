package com.keplux.todo_app.subtask;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SubtaskController {

  @Autowired
  private SubtaskService subtaskService;

  @GetMapping("/subtasks")
  public ResponseEntity<List<Subtask>> getAllSubtasks() {
    return subtaskService.getAllSubtasks();
  }

  @GetMapping("/subtasks/{subtaskId}")
  public ResponseEntity<Subtask> getSubtaskById(@PathVariable Long subtaskId) {
    return subtaskService.getSubtasksById(subtaskId);
  }

  @GetMapping("/subtasks/{taskId}/subtasks")
  public ResponseEntity<List<Subtask>> getAllTaskSubtasks(@PathVariable Long taskId) {
    return subtaskService.getAllTaskSubtasks(taskId);
  }

  @PostMapping("/subtasks")
  public ResponseEntity<Subtask> createSubtask(@RequestBody Subtask newSubtask) {
    return subtaskService.createSubtask(newSubtask);
  }

  @PutMapping("/subtasks/{subtaskId}")
  public ResponseEntity<Subtask> updateSubtask(@PathVariable Long subtaskId,
      @RequestBody Subtask updatedSubtask) {
    return subtaskService.updateSubtask(subtaskId, updatedSubtask);
  }
}
